const OWNER = "AzimMuradov";
const REPO = "stardew-valley-designer";


function fetchLatestRelease(owner, repo) {
    const url = `https://api.github.com/repos/${owner}/${repo}/releases/latest`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const version = data.tag_name.slice(1);

            const releaseVersionElement = document.getElementById("release-version");
            if (releaseVersionElement) {
                releaseVersionElement.innerText = version;
            }

            const updateHref = (id, newHref) => {
                const element = document.getElementById(id);
                if (element) {
                    element.href = newHref;
                }
            };

            updateHref("download-zip", `https://github.com/${owner}/${repo}/releases/latest/download/stardew-valley-designer-${version}-windows-amd64.zip`);
            updateHref("download-msix", `https://github.com/${owner}/${repo}/releases/latest/download/stardew-valley-designer-${version}.x64.msix`);
            updateHref("download-deb", `https://github.com/${owner}/${repo}/releases/latest/download/stardew-valley-designer_${version}_amd64.deb`);
            updateHref("download-tar-gz", `https://github.com/${owner}/${repo}/releases/latest/download/stardew-valley-designer-${version}-linux-amd64.tar.gz`);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}


fetchLatestRelease(OWNER, REPO);