
  // Pagination

  $("a.page-link").on('click', function(){
    $(this).css({"border": "1px solid red",  "border-radius": "3%"});
  });




  $( "#repeat-password" ).change(function() {
         if ($(this).val().trim() != $('#password').val().trim()) {
             $('#submit-button').prop('disabled', true);
             $('#error').text('passwords don\'t match');
             $(this).css({"border": "1px solid red",  "border-radius": "3%"});
             $("#password").css({"border": "1px solid red",  "border-radius": "3%"});
         } else {
             $('#submit-button').prop('disabled', false);
               $('#error').text('');
               $(this).css({"border": "none", "border-radius": "none"});
               $("#password").css({"border": "none", "border-radius": "none"});
         }
    });
  $( "#password" ).change(function() {
         if ($(this).val().trim() != $('#repeat-password').val().trim()) {
             $('#submit-button').prop('disabled', true);
             $('#error').text('passwords don\'t match');
             $(this).css({"border": "1px solid red",  "border-radius": "3%"});
             $("#repeat-password").css({"border": "1px solid red",  "border-radius": "3%"});
         } else {
             $('#submit-button').prop('disabled', false);
               $('#error').text('');
               $(this).css({"border": "none", "border-radius": "none"});
               $("#repeat-password").css({"border": "none", "border-radius": "none"});
         }
    });

     /*==================================================================
        [ Focus Contact2 ]*/
        $('.input100').each(function(){
            $(this).on('blur', function(){
                if($(this).val().trim() != "") {
                    $(this).addClass('has-val');
                }
                else {
                    $(this).removeClass('has-val');
                }
            })
        })


        /*==================================================================
        [ Validate ]*/
        var input = $('.validate-input .input100');

        $('.validate-form').on('submit',function(){
            var check = true;

            for(var i=0; i<input.length; i++) {
                if(validate(input[i]) == false){
                    showValidate(input[i]);
                    check=false;
                }
            }

            return check;
        });


        $('.validate-form .input100').each(function(){
            $(this).focus(function(){
               hideValidate(this);
            });
        });

        function validate (input) {
            if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
                if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                    return false;
                }
            }
            else {
                if($(input).val().trim() == ''){
                    return false;
                }
            }
        }

        function showValidate(input) {
            var thisAlert = $(input).parent();

            $(thisAlert).addClass('alert-validate');
        }

        function hideValidate(input) {
            var thisAlert = $(input).parent();

            $(thisAlert).removeClass('alert-validate');
        }


