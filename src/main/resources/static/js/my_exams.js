function showButtons(examId) {
    var buttons = document.getElementsByClassName("btn-block");
    for (var i = 0; i < buttons.length; i++) {
        var button = buttons[i];
        button.style.display = 'none';
    }
    buttons[examId].style.display = 'block';
}