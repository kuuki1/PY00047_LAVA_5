$(function() {
    // Custom Validation Methods
    jQuery.validator.addMethod("space", function(value, element) {
        return this.optional(element) || !/\s/.test(value);
    }, "Spaces are not allowed");

    jQuery.validator.addMethod("lettersWithSpace", function(value, element) {
        return this.optional(element) || /^[a-zA-Z\s]+$/.test(value);
    }, "Only letters and spaces allowed");

    jQuery.validator.addMethod("numericOnly", function(value, element) {
        return this.optional(element) || /^[0-9]+$/.test(value);
    }, "Numbers only allowed");

    jQuery.validator.addMethod("all", function(value, element) {
        return this.optional(element) || /^[\w\s.,-]+$/.test(value);
    }, "Invalid characters");

    jQuery.validator.addMethod("startsWithZero", function(value, element) {
        return this.optional(element) || /^0[0-9]{9}$/.test(value);
    }, "Mobile number must start with 0");

    jQuery.validator.addMethod("customEmail", function(value, element) {
        return this.optional(element) || /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value);
    }, "Please enter a valid email address");

    jQuery.validator.addMethod("filesize", function(value, element, param) {
        return this.optional(element) || (element.files[0].size <= param * 1024 * 1024);
    }, "File size must be less than {0}MB");

    // User Register Validation
    $("#userRegister").validate({
        rules: {
            name: {
                required: true,
                lettersWithSpace: true
            },
            email: {
                required: true,
                space: true,
                email: true,
                customEmail: true
            },
            mobileNumber: {
                required: true,
                space: true,
                numericOnly: true,
                minlength: 10,
                maxlength: 10,
                startsWithZero: true
            },
            password: {
                required: true,
                space: true,
                minlength: 10,
                maxlength: 10
            },
            confirmpassword: {
                required: true,
                space: true,
                equalTo: "#pass"
            },
            address: {
                required: true,
                all: true
            },
            city: {
                required: true,
                space: false
            },
            pincode: {
                required: true,
                space: true,
                numericOnly: true,
                minlength: 6,
                maxlength: 6
            }
        },
        messages: {
            name: {
                required: "Name is required",
                lettersWithSpace: "Name can only contain letters and spaces"
            },
            email: {
                required: "Email is required",
                space: "Spaces are not allowed",
                email: "Please enter a valid email",
                customEmail: "Invalid email format"
            },
            mobileNumber: {
                required: "Mobile number is required",
                space: "Spaces are not allowed",
                numericOnly: "Invalid mobile number (numbers only)",
                minlength: "Must be 10 digits",
                maxlength: "Must be 10 digits",
                startsWithZero: "Mobile number must start with 0"
            },
            password: {
                required: "Password is required",
                space: "Spaces are not allowed"
            },
            confirmpassword: {
                required: "Confirm password is required",
                space: "Spaces are not allowed",
                equalTo: "Passwords do not match"
            },
            address: {
                required: "Address is required",
                all: "Invalid address"
            },
            city: {
                required: "City is required",
                space: "Spaces are not allowed"
            },
            pincode: {
                required: "Pincode is required",
                space: "Spaces are not allowed",
                numericOnly: "Invalid pincode (numbers only)",
                minlength: "Must be 6 digits"
            }
        }
    });

    // Orders Validation
    $("#orders").validate({
        rules: {
            firstName: {
                required: true
            },
            lastName: {
                required: true
            },
            email: {
                required: true,
                space: true,
                email: true
            },
            mobileNo: {
                required: true,
                space: true,
                numericOnly: true,
                minlength: 10,
                maxlength: 10
            },
            address: {
                required: true,
                all: true
            },
            city: {
                required: true,
                space: false
            },
            pincode: {
                required: true,
                space: true,
                numericOnly: true,
                minlength: 6
            },
            paymentType: {
                required: true
            }
        },
        messages: {
            firstName: {
                required: 'First name is required'
            },
            lastName: {
                required: 'Last name is required'
            },
            email: {
                required: 'Email is required',
                space: 'Spaces are not allowed',
                email: 'Invalid email'
            },
            mobileNo: {
                required: 'Mobile number is required',
                space: 'Spaces are not allowed',
                numericOnly: 'Invalid mobile number',
                minlength: 'Mobile number must be 10 digits'
            },
            address: {
                required: 'Address is required',
                all: 'Invalid address'
            },
            city: {
                required: 'City is required',
                space: 'Spaces are not allowed'
            },
            pincode: {
                required: 'Pincode is required',
                space: 'Spaces are not allowed',
                numericOnly: 'Invalid pincode',
                minlength: 'Pincode must be 6 digits'
            },
            paymentType: {
                required: 'Please select payment type'
            }
        }
    });

    // Reset Password Validation
    $("#resetPassword").validate({
        rules: {
            password: {
                required: true,
                space: true
            },
            confirmPassword: {
                required: true,
                space: true,
                equalTo: '#pass'
            }
        },
        messages: {
            password: {
                required: 'Password is required',
                space: 'Spaces are not allowed'
            },
            confirmpassword: {
                required: 'Confirm password is required',
                space: 'Spaces are not allowed',
                equalTo: 'Passwords do not match'
            }
        }
    });

    // User Profile Validation
    $("#proFile").validate({
        rules: {
            name: {
                required: true,
                lettersWithSpace: true,
                minlength: 2,
                maxlength: 300
            },
            mobileNumber: {
                required: true,
                numericOnly: true,
                minlength: 10,
                maxlength: 10,
                startsWithZero: true
            },
            address: {
                required: true,
                all: true,
                minlength: 5,
                maxlength: 300
            },
            city: {
                required: true,
                lettersWithSpace: true,
                minlength: 2,
                maxlength: 300
            },
            pincode: {
                required: true,
                numericOnly: true,
                minlength: 6,
                maxlength: 6
            },
            img: {
                extension: "jpg|jpeg|png",
                filesize: 2
            }
        },
        messages: {
            name: {
                required: "Please enter your name!",
                lettersWithSpace: "Name can only contain letters and spaces!",
                minlength: "Name must be at least 2 characters long!",
                maxlength: "Name cannot exceed 50 characters!"
            },
            mobileNumber: {
                required: "Please enter your mobile number!",
                numericOnly: "Mobile number must contain only digits!",
                minlength: "Mobile number must be exactly 10 digits!",
                maxlength: "Mobile number must be exactly 10 digits!",
                startsWithZero: "Mobile number must start with 0!"
            },
            address: {
                required: "Please enter your address!",
                all: "Address contains invalid characters!",
                minlength: "Address must be at least 5 characters long!",
                maxlength: "Address cannot exceed 100 characters!"
            },
            city: {
                required: "Please enter your city!",
                lettersWithSpace: "City can only contain letters and spaces!",
                minlength: "City must be at least 2 characters long!",
                maxlength: "City cannot exceed 50 characters!"
            },
            pincode: {
                required: "Please enter your postal code!",
                numericOnly: "Postal code must contain only digits!",
                minlength: "Postal code must be exactly 6 digits!",
                maxlength: "Postal code must be exactly 6 digits!"
            },
            img: {
                extension: "Only .jpg, .jpeg, or .png image files are allowed!",
                filesize: "Image file size cannot exceed 2MB!"
            }
        },
        errorElement: "div",
        errorPlacement: function(error, element) {
            error.addClass("text-danger mt-1");
            error.insertAfter(element);
        },
        submitHandler: function(form) {
            form.submit();
        }
    });
});
