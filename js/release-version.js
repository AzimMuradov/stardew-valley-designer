const OWNER = "AzimMuradov";
const REPO = "stardew-valley-designer";
const CACHE_TIME = 60 * 1000; // 1 minute timeout for GitHub API requests


function fetchLatestRelease(owner, repo) {
    const url = `https://api.github.com/repos/${owner}/${repo}/releases/latest`;
    let cache = null;

    try {
        cache = JSON.parse(localStorage.getItem("apiCache"));
    } catch(e) {
        console.error("Error parsing JSON from localStorage:", e)
    }

    const now = new Date();

    if (cache && cache.version && now - new Date(cache.lastCheck) < CACHE_TIME) {
        updateUI(cache.version);
        return;
    }

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => handleApiResponse(data))
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
            if (cache && cache.version) {
                updateUI(cache.version);
            }
            return;
        });
}


function handleApiResponse(data) {
    const version = data.tag_name.slice(1);
    const newCache = {
        lastCheck: new Date().toISOString(),
        version: version,
    };

    localStorage.setItem("apiCache", JSON.stringify(newCache));
    updateUI(version);
}

function updateUI(version) {
    const updateText = (id, text) => {
        const element = document.getElementById(id);
        if (element) {
            element.innerText = text;
        }
    };

    const updateHref = (id, newHref) => {
        const element = document.getElementById(id);
        if (element) {
            element.href = newHref;
        }
    };

    const url = `https://github.com/AzimMuradov/stardew-valley-designer/releases/download/v${version}/stardew-valley-designer`

    updateText("release-version", version);
    updateText("release-version-linux", version);
    updateHref("download-zip", `${url}-${version}-windows-amd64.zip`);
    updateHref("download-msix", `${url}-${version}.x64.msix`);
    updateHref("download-deb", `${url}_${version}_amd64.deb`);
    updateHref("download-tar-gz", `${url}-${version}-linux-amd64.tar.gz`);
}

fetchLatestRelease(OWNER, REPO);
