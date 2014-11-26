/* ========================================================================
 * Copyright 2014 G3xtreme
 *
 * Licensed under the MIT, The MIT License (MIT)
 * Copyright (c) 2014 G3xtreme

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 * ========================================================================


Source generated by CrudMaker version 1.0.0.201411201032

*/
define(['model/_responsableModel'], function() {
    App.Model.ResponsableModel = App.Model._ResponsableModel.extend({
            initialize: function(){
            this.set('rol','admin');
        },
            validate: function(attrs,options){
            var validationMessage = "";
            if(!attrs.name){
                validationMessage = "El nombre no puede ser vacio.";
            }
            if(!attrs.correo)
            {
                alidationMessage = "El correo no puede ser vacio";
            }
            if(!attrs.correo.contains("@")&& !attrs.correo.contains(".")){
                validationMessage = "El correo debe ser del formato: algo@algo.com ";
            }
            if(!attrs.tipo){
                validationMessage = "El tipo no puede ser vacio.";
            }
            if(attrs.rolrespId.equals("None"))
            {
                validationMessage = "Se debe especificar el rol del responsable.";
            }
            if(validationMessage.length>0){
               return validationMessage;
            }
        }

    });

    App.Model.ResponsableList = App.Model._ResponsableList.extend({
        model: App.Model.ResponsableModel
    });

    return  App.Model.ResponsableModel;

});