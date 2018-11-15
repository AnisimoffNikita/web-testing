/*$('#question').click(function(){
   if ( $( "#answers" ).is( ":hidden" ) ) {
    $( "#answers" ).show('200');
  } else {
    $( "#answers" ).hide('200');
  }
}); */


function onSelectChange(questionId) {

    var questionCard = document.getElementById('question' + questionId);

    var elems = questionCard.getElementsByClassName("form-check-input");

    var selectedIndex = questionCard.getElementsByTagName('select')[0].options.selectedIndex;

    if (selectedIndex < 2) {
        for (var i = 0; i < elems.length; i++) {
            var checkbox = document.createElement('input');
            checkbox.className = "form-check-input";
            if (selectedIndex === 0)
                checkbox.type = "radio";
            else
                checkbox.type = "checkbox";
            elems[i].parentNode.replaceChild(checkbox, elems[i]);
        }

        var rightAnswer = questionCard.getElementsByClassName("right-answer-input");
        if (rightAnswer.length !== 0) {
            rightAnswer[0].parentNode.remove();

            var row = questionCard.getElementsByClassName("row")[0];

            var col12 = document.createElement('div');
            col12.className = "col-12 mt-3 answers-list add-answer-block";

            var col10 = document.createElement('div');
            col10.className = "col-10 add-answer-block";

            var inputAns = document.createElement('input');
            inputAns.type = "text";
            inputAns.className = "form-control mb-3 answer-input";
            inputAns.placeholder = "Вариант ответа";

            col10.appendChild(inputAns);

            var col2 = document.createElement('div');
            col2.className = "col-2 add-answer-block";

            var button = document.createElement('button');
            button.type = "button";
            button.className = "btn btn-outline-primary";
            button.innerHTML = "Добавить";

            button.addEventListener("click", function(){ onAddAnswerClick(questionId) });  // костыль Тиена

            col2.appendChild(button);

            row.appendChild(col12);
            row.appendChild(col10);
            row.appendChild(col2);

        }
    }

    else if (selectedIndex === 2) {
        var removeElems = questionCard.getElementsByClassName("add-answer-block");

        while (removeElems.length > 0) {
            removeElems[0].remove();
        }

        var row = questionCard.getElementsByClassName("row")[0];

        var col12 = document.createElement('div');
        col12.className = "col-12 add-answer-block";

        var inputAns = document.createElement('input');
        inputAns.type = "text";
        inputAns.className = "form-control my-3 right-answer-input";
        inputAns.placeholder = "Правильный ответ";

        col12.appendChild(inputAns);

        row.appendChild(col12);

    }
};

function onAddAnswerClick(questionId){

    var questionCard = document.getElementById('question' + questionId);
    var answerInput = questionCard.getElementsByClassName('answer-input');

    var answer = answerInput[0].value;

    if (answer === "")
        return;

    var checkbox = document.createElement('input');
    checkbox.className = "form-check-input";
    checkbox.setAttribute('name', questionId);

    var selectedIndex = questionCard.getElementsByTagName('select')[0].options.selectedIndex;
    if (selectedIndex === 0)
        checkbox.type = "radio";
    else if (selectedIndex === 1)
        checkbox.type = "checkbox";


    var div = document.createElement('div');
    div.className = "form-check mb-1";

    var v = document.createElement('input');
    v.className = "form-control variant";
    v.value = answer;

    div.appendChild(checkbox);
    div.appendChild(v);

    questionCard.getElementsByClassName("answers-list")[0].appendChild(div);

    answerInput[0].value = "";
};

function onAddQuestionClick () {

    var questionCard = document.createElement('div');
    questionCard.className = "card mb-4 question";

    var id = document.getElementsByClassName('question').length;
    questionCard.id = "question" + id;

    var cardBody = document.createElement('div');
    cardBody.className = "card-body";

    var container = document.createElement('div');
    container.className = "container";

    var row = document.createElement('div');
    row.className = "row";

    var col9 = document.createElement('div');
    col9.className = "col-9";

    var input = document.createElement('input');
    input.type = "text";
    input.className = "form-control questionText";
    input.placeholder = "Текст вопроса";

    col9.appendChild(input);

    var col3 = document.createElement('div');
    col3.className = "col-3";

    var select = document.createElement('select');
    select.className = "form-control";

    var option1 = document.createElement('option');
    option1.innerHTML = "Один ответ";

    var option2 = document.createElement('option');
    option2.innerHTML = "Несколько ответов";

    var option3 = document.createElement('option');
    option3.innerHTML = "Без выбора ответа";

    select.appendChild(option1);
    select.appendChild(option2);
    select.appendChild(option3);

    select.addEventListener("change", function(){ onSelectChange(id) });

    col3.appendChild(select);

    var col12 = document.createElement('div');
    col12.className = "col-12 mt-3 answers-list add-answer-block";

    var col10 = document.createElement('div');
    col10.className = "col-10 add-answer-block";

    var inputAns = document.createElement('input');
    inputAns.type = "text";
    inputAns.className = "form-control mb-3 answer-input";
    inputAns.placeholder = "Вариант ответа";

    col10.appendChild(inputAns);

    var col2 = document.createElement('div');
    col2.className = "col-2 add-answer-block";

    var button = document.createElement('button');
    button.type = "button";
    button.className = "btn btn-outline-primary";
    button.innerHTML = "Добавить";

    col2.appendChild(button);

    row.appendChild(col9);
    row.appendChild(col3);
    row.appendChild(col12);
    row.appendChild(col10);
    row.appendChild(col2);

    container.appendChild(row);
    cardBody.appendChild(container);
    questionCard.appendChild(cardBody);

    button.addEventListener("click", function(){ onAddAnswerClick(id) });  // костыль Тиена

    document.getElementById("questionList").appendChild(questionCard);

};