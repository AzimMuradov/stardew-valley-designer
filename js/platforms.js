function unavailable() {
    document.getElementById("no-go").style = "display: block";
    document.getElementById("panes").style = "display: none";
}

function showOS(os, arch) {
    // Hide the elements with id pane-* so only the one with id pane-os is shown.
    for (const o of ["win", "mac", "linux"]) {
        var s = ""
        if (o != os)
            s = "display: none";
        let pane = document.getElementById("pane-" + o);
        if (pane)
            pane.style = s;
    }

    // Ensure the user sees the right button for their CPU arch, if we know it.
    if (os == "mac") {
        if (arch == "amd64") {
            let el = document.getElementById("mac-aarch64");
            if (el)
                el.style = "display: none";
        } else if (arch == "aarch64") {
            let el = document.getElementById("mac-amd64");
            if (el)
                el.style = "display: none";
        }
    }
}

function afterProbe(isUnavailable, os, arch) {
    if (os == "other" || isUnavailable) {
        unavailable();
    } else {
        showOS(os, arch);
    }
}

const panes = document.getElementById("panes");
if (panes.children.length == 1) {
    // Only one OS supported. Do nothing - it will be visible already.
} else if (document.location.hash) {
    showOS(document.location.hash.substring(1));
} else {
    // Auto detect.
    const uaData = navigator.userAgentData;
    if (uaData != undefined) {
        // This is a new API. It's the future of user-agent sniffing and also usefully exposes the underlying CPU architecture which
        // we need to serve efficient downloads. Unfortunately both CPU arch and OS are considered "high entropy" for some reason
        // and the API to get it is asynchronous even though it returns immediately. Additionally it doesn't expose the version of
        // Windows the user has, and Safari doesn't support it. But it's all we're offered.
        navigator.userAgentData.getHighEntropyValues(["architecture", "bitness", "platform"]).then(uaData => {
            const platform = uaData.platform;

            var arch = "amd64";
            if (uaData.architecture == "arm")
                arch = "aarch64"

            // Valid values are: "Android", "Chrome OS", "iOS", "Linux", "macOS", "Windows", or "Unknown".
            var os;
            if (platform == "Windows") {
                os = "win";
            } else if (platform == "macOS") {
                os = "mac"
            } else if (platform == "Linux") {
                os = "linux"
            }
            else os = "other";

            // Not available on mobile platforms currently.
            let isUnavailable = navigator.userAgentData.mobile;
            afterProbe(isUnavailable, os, arch);
        });
    } else {
        // Older way.
        let appVersion = navigator.appVersion;
        var os;
        if (appVersion.indexOf("Win") !== -1) os = "win";
        else if (appVersion.indexOf("Mac") !== -1) os = "mac";
        else if (appVersion.indexOf("X11") !== -1 || appVersion.indexOf("Linux") !== -1) os = "linux"
        else os = "other";

        // CPU architecture in older API is useless. Safari lies and says Intel even on ARM.
        afterProbe(false, os);
    }
}

// Make code copyable by clicking the button.
Array.from(document.getElementsByClassName("copy-button")).forEach((el) => {
    el.addEventListener("click", (event) => {
        const mimeType = 'text/plain';
        const data = el.parentElement.parentElement.parentElement.childNodes[0].textContent.split("\n").map(str => str.replace("$\xa0", "").replace("PS>\xa0", "").trim()).join("\n");

        try {
            const promise = Promise.resolve(new Blob([data], {
                type: mimeType
            }));

            const clipboardItem = new ClipboardItem({
                [mimeType]: promise
            }, {
                presentationStyle: "unspecified"
            });

            navigator.clipboard.write([clipboardItem])
        } catch (e) {
            // Try again with the older API. This is for Firefox.
            navigator.clipboard.writeText(data);
        }

        // Make the tooltip appear to confirm to the user.
        var index = document.styleSheets[0].insertRule(".tooltip:before, .tooltip:after { opacity:1; }", document.styleSheets[0].cssRules.length);
        setTimeout(function() { document.styleSheets[0].removeRule(index) }, 5000);
    })
})