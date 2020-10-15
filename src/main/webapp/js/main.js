// Personal modal form



//registation, login
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



                // Personal form
                $(document).ready(
                    function() {
                         $("#year").text(new Date().getFullYear());
                         // Personal counter
                                        let inputs = $('.requestfield');
                                        $('#pricecounternav').on('click', function(){
                                           let check = true;
                                           for(let i=0; i<inputs.length; i++) {
                                               $(inputs[i]).val('');
                                           }
                                            $('#showrequestcost').css({'display':'none'});
                                           $("#myModalPersonalCounter").modal('show');
                                        });




                                         $('.requestfield').on('change',function(e){

                                             let check = true;
                                             for(let i=0; i<inputs.length; i++)
                                             {
                                                 if(validate(inputs[i]) == false){
                                                     $(inputs[i]).css({"border": "1px solid red",  "border-radius": "5%"});
                                                     check=false;
                                                 } else {
                                                    $(inputs[i]).css({"border": "none"});
                                                 }
                                             }
                                             if ($('#requestcityfromid').val().trim()==$('#requestcitytoid').val().trim()) {
                                                $('#requestcityfromid').css({"border": "1px solid yellow",  "border-radius": "5%"});
                                                $('#requestcitytoid').css({"border": "1px solid yellow",  "border-radius": "5%"});
                                             }

                                             let values = getData(inputs);
                                             let i = 0;
                                            if (check) {

                                              $.ajax({url: "/gtc/controller?command=personalCounterForm",
                                              data: {
                                                'requestcityfromid' : values[i++],
                                                'requestcitytoid' : values[i++],
                                                'requestweight' : values[i++],
                                                'requestlength' : values[i++],
                                                'requestwidth' : values[i++],
                                                'requestheight' : values[i++],
                                                'requestdeliverydate' : values[i++],
                                                'requestcontenttypename' : values[i++],
                                              },
                                              success: function(result){
                                                 console.log('result', result);
                                                 $('#requestcost').text(result.requestcost);
                                                 check = true;
                                              },
                                              error: function(result) {
                                                    check = false;
                                                }
                                              });
                                            }
                                            if (check) {
                                                $('#showrequestcost').css({'display':'inherit'});
                                            } else {
                                                $('#showrequestcost').css({'display':'none'});
                                            }
                                         });

                                        // Owl-Carousel-JavaScript
                                        var owl = $('.owl-carousel');
                                  		owl.owlCarousel({
                                  			items: 3,
                                  			loop: true,
                                  			margin: 10,
                                  			autoplay: true,
                                  			autoplayTimeout: 1000,
                                  			autoplayHoverPause: true,
                                  			responsive: {
                                  				0: {
                                  					items: 1
                                  				},
                                  				600: {
                                  					items: 2
                                  				},
                                  				1000: {
                                  					items: 3
                                  				}
                                  			}
                                  		});
                                  		$('.play').on('click', function () {
                                  			owl.trigger('play.owl.autoplay', [1000])
                                  		});
                                  		$('.stop').on('click', function () {
                                  			owl.trigger('stop.owl.autoplay')
                                  		});

                                        // smooth scrolling-bottom-to-top
                                            $(".scroll").click(function (event) {
                                                event.preventDefault();
                                                $('html,body').animate({
                                                    scrollTop: $(this.hash).offset().top
                                                }, 1000);
                                            });

                                         /*
                                            var defaults = {
                                            containerID: 'toTop', // fading element id
                                            containerHoverID: 'toTopHover', // fading element hover id
                                            scrollSpeed: 1200,
                                            easingType: 'linear'
                                            };
                                        */
                                        $().UItoTop({
                                            easingType: 'easeOutQuart'
                                        });


                    }
                );
                                      function validate (input) {

                                             if($(input).attr('type') == 'select') {
                                                 if($(input).val() == 'Choose...') {
                                                    return false;
                                                 }
                                             }
                                             else {
                                                 if($(input).val() == ''){
                                                     return false;
                                                 }
                                             }
                                         }


                                        function getData(inputs) {
                                            let data = [];
                                            for(let i=0; i<inputs.length; i++) {
                                                let key = $(inputs[i]).prop('id');
                                                let value = $(inputs[i]).val();
                                                data.push(value);
                                            }
                                            return data;
                                        }



