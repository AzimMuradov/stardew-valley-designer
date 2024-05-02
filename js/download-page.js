const btnDownload = document.getElementById("download-btn");

document.addEventListener('DOMContentLoaded', () => {
    try {
        const parser = new UAParser();
        const result = parser.getResult();
        const os = result.os.name;

        if (os === "Linux" && btnDownload) {
            btnDownload.href = "https://stardewdesigner.com/download-linux";
        }
    } catch (error) {
        console.error("Error parsing user agent:", error);
    }
});