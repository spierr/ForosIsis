define(['component/_diaMasterComponent'],function(_DiaMasterComponent) {
    App.Component.DiaMasterComponent = _DiaMasterComponent.extend({
		postInit: function(){
			//Escribir en este servicio las instrucciones que desea ejecutar al inicializar el componente
		}
    });

    return App.Component.DiaMasterComponent;
});