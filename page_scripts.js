document.addEventListener("DOMContentLoaded", function() {
    fetch('header.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('header-container').innerHTML = data;
        });
});

document.addEventListener("DOMContentLoaded", function() {
    fetch('content.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('content-container').innerHTML = data;
        });
});

document.addEventListener("DOMContentLoaded", function() {
    fetch('footer.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('footer-container').innerHTML = data;
        });
});
