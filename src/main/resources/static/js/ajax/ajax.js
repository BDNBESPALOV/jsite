// var servResponse = document.querySelector('#response');
//
//    document.forms.ourForm.onsubmit = function(e){
//
//     e.preventDefault();
//    var userInput = document.forms.ourForm.ourForm_inp.value;
//
//    userInput = encodeURIComponent(userInput);
//
//    var xhr = new XMLHttpRequest();
//
//    xhr.open('POST','/textInfo/text',true);
//
//    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
//
//    xhr.onreadystatechange =
//    //setInterval(
//    function(){
//         servResponse.textContent = xhr.responseText;
//        if(xhr.readyState === 4 && xhr.status === 200){
//            servResponse.textContent = xhr.responseText;
//
//        if(xhr.response.includes("alert")){
//             if(servResponse.classList.contains("alert-success")) { servResponse.classList.remove("alert-success"); }
//             if(servResponse.classList.contains("alert-danger")) { servResponse.classList.remove("alert-danger"); }
//             servResponse.classList.toggle(
//
//             xhr.response.substring(
//              (xhr.response.length - 13)
//             )
//
//             );
//          }
//
//
//        }
//        console.log(xhr.responseText)
//    };
//    //, 1000);
//
//    xhr.send('ourForm_inp='+userInput);
//};
// var servResponseX = document.querySelector('#responseX');
//
//    document.forms.ourFormX.onsubmit = async function(){
//
////    let response = await fetch('https://api.github.com/repos/javascript-tutorial/en.javascript.info/commits');
////    servResponseX.textContent = await response.text();
//
//    fetch('https://api.github.com/repos/javascript-tutorial/en.javascript.info/commits').then(function(response) {
//      response.text().then(function(text) {
//        servResponseX.textContent = text;
//      });
//    });
//
//     };

//var servResponse = document.querySelector('#contentExecuteSQL');
////var x = document.getElementById("contentExecuteSQL");
////    x.onsubmit =
//    document.forms.preForm.onsubmit = function(e){
//
//     e.preventDefault();
//     var xhr = new XMLHttpRequest();
//
//    xhr.open('POST','/textInfo/executeSQL',true);
//
//    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
//
//    xhr.onreadystatechange =    function(){
//         servResponse.textContent = xhr.responseText;
//        if(xhr.readyState === 4 && xhr.status === 200){
//            servResponse.textContent = xhr.responseText;
//
//        }
//        console.log(xhr.responseText)
//    };
//
//    xhr.send('ourForm_inp='+"userInput");
//};
     var servResponseX = document.querySelector('#responseX');

   document.forms.ourFormX.onsubmit = async function m () {
    let response = await fetch('https://api.github.com/repos/javascript-tutorial/en.javascript.info/commits');

    let text = await response.text(); // прочитать тело ответа как текст

    servResponseX.textContent = text;
    console.log(text);
};