addElementToPage("header.html", "header-container")
addElementToPage("footer.html", "footer-container")


function addElementToPage(htmlFile, elementID) {
    document.addEventListener("DOMContentLoaded", function() {
        fetch(htmlFile)
            .then(response => response.text())
            .then(data => {
                document.getElementById(elementID).innerHTML = data;
            });
    });
}
