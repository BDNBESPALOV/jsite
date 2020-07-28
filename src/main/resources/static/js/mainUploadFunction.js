        function mainUploadFunction() {
            myUploadPatch.setAttribute("action","executeMainUpload");
            document.getElementById("myUploadPatch").submit();
            location.reload();
        }

        function clearTestSQL() {
        	$('#testSQL').html('');
        }