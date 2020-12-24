var servResponse = document.querySelector('#contentExecuteSQL');
    document.forms.preForm.onsubmit =  async function(e){

     e.preventDefault();
     var xhr = new XMLHttpRequest();

    xhr.open('POST','/updater/executeSQL',true);

    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function(){
         //servResponse.textContent = xhr.responseText;
        if(xhr.readyState === 4 && xhr.status === 200){
            servResponse.textContent =  await xhr.responseText ;
            t();
        }
        console.log(xhr.responseText)

    };

    xhr.send('ourForm_inp='+"userInput");
};

function  t () {  !function ($) {
            $(function(){
               window.prettyPrint && prettyPrint()
            })
        }(window.jQuery)
        }