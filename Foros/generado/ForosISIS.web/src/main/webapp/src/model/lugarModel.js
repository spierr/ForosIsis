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
define(['model/_lugarModel'], function() {
    App.Model.LugarModel = App.Model._LugarModel.extend({

 		validate: function(attrs,options){
            var validationMessage = "";
            if(!attrs.name){
                validationMessage = "El nombre no puede ser vacio.";
            }
            if(!attrs.direccion){
                validationMessage = "La dirección no puede ser vacia.";
            }
            if(!attrs.costo){
                validationMessage = "El costo no puede ser vacio";
            }
//            if(!attrs.direccion.contains("#")||!attrs.direccion.contains("-")){
//                validationMessage = " La direccion debe ser del formato calle/carrera numero # numero-numero.";
//            }
            if(parseInt(attrs.costo)< 0 )
            {
                validationMessage = "El costo debe ser un numero positivo.";
            }
            if(validationMessage.length>0){
               return validationMessage;
            }
        }

    });

    App.Model.LugarList = App.Model._LugarList.extend({
        model: App.Model.LugarModel
    });

    return  App.Model.LugarModel;

});