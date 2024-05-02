// Path to the folder with common HTML elements
const COMMON_HTML_FOLDER = "common";


function getPath(...parts) {
    return "/" + (parts).join("/");
}


function addElementToPage(htmlFileName, elementID) {
        const path = getPath(COMMON_HTML_FOLDER, htmlFileName)

        fetch(path)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.text();
            })
            .then(data => {
                document.getElementById(elementID).innerHTML = data;
            })
            .catch(error => {
                console.error("Failed to load the HTML file:", error);
            });
}


document.addEventListener("DOMContentLoaded", function() {
    addElementToPage("header.html", "header-container")
    addElementToPage("footer.html", "footer-container")
});