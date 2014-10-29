define(['component/_faseMasterComponent'],function(_FaseMasterComponent) {
    App.Component.FaseMasterComponent = _FaseMasterComponent.extend({
		postInit: function(){
			//Escribir en este servicio las instrucciones que desea ejecutar al inicializar el componente
		}
    });

    return App.Component.FaseMasterComponent;
});