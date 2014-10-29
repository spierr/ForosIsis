define(['component/_coordinadorMasterComponent'],function(_CoordinadorMasterComponent) {
    App.Component.CoordinadorMasterComponent = _CoordinadorMasterComponent.extend({
		postInit: function(){
			//Escribir en este servicio las instrucciones que desea ejecutar al inicializar el componente
		}
    });

    return App.Component.CoordinadorMasterComponent;
});