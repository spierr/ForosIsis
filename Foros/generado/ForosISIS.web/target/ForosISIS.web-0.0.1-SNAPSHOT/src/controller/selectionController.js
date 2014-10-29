    define([], function() {
    App.Controller.SelectionController = Backbone.View.extend({
        initialize: function(options) {
            var self = this;
            this.componentId = options.componentId;
            jQuery("<style type='text/css'>.bgtransparent{position:fixed;left:0;top:0;background-color:#000;opacity:.6;filter:alpha(opacity=60)}.bgmodal{position:fixed;font-family:arial;font-size:1em;border:.05em solid #000;overflow:auto;background-color:#fff} </style>").appendTo("head");
            App.Utils.loadTemplate('selectionList');
            this.selectionListTemplate = _.template($('#selectionList').html());
            Backbone.on(this.componentId+'-showSelection', function(params) {
                self.showSelectionList(params);
            });

            Backbone.on(this.componentId+'-selection-add', function(params) {
                self.addSelectionList(params);
            });
            Backbone.on(this.componentId+'-selection-cancel', function(params) {
                self.cancelSelectionList(params);
            });
        },
                
        showSelectionList: function(params) {
            var bgdiv = $('<div>').attr({
                class: 'bgtransparent',
                id: 'bgtransparent'
            });
            // agregamos nuevo div a la pagina
            $('body').append(bgdiv);
            // obtenemos ancho y alto de la ventana del explorer
            var wscr = $(window).width();
            var hscr = $(window).height();
            //establecemos las dimensiones del fondo
            $('#bgtransparent').css("width", wscr);
            $('#bgtransparent').css("height", hscr);
            // ventana modal
            // creamos otro div para la ventana modal y dos atributos
            var moddiv = $('<div>').attr({
                class: 'bgmodal',
                id: 'bgmodal'
            });

            // agregamos div a la pagina
            $('body').append(moddiv);

            // redimensionamos para que se ajuste al centro y mas
            this.resize();
            this._renderSelectionList(params);
        },
        _renderSelectionList: function(params) {
            this.list = params.list;
            var name = params.name;
            var title = params.title;
            $('#bgmodal').html(this.selectionListTemplate({'list': this.list.models, 'name':name, 'title':title, 'componentId':this.componentId}));
            
        },
        addSelectionList: function(params) {
            var selectedIds = $('#addForm').serializeObject();
            if(!Array.isArray(selectedIds.selection)){
            	selectedIds.selection = [selectedIds.selection];
            }
            var resp = new Array();
            
            _.each(this.list.models,function(m){
                _.each(selectedIds.selection,function(id){
                    if(m.id == id){
                        resp.push(m.attributes);
                    }
                });
            });
            
            Backbone.trigger(this.componentId+'-post-selection',  resp);
            $('#bgmodal').remove();
            $('#bgtransparent').remove(); 
        },
        cancelSelectionList: function(params) {
            $('#bgmodal').remove();
            $('#bgtransparent').remove();
        },
        resize: function() {
            var wscr = $(window).width();
            var hscr = $(window).height();

            // estableciendo dimensiones de fondo
            $('#bgtransparent').css("width", wscr);
            $('#bgtransparent').css("height", hscr);
            $('#bgtransparent').css("z-index", 1000);

            // estableciendo tama�o de la ventana modal
            $('#bgmodal').css("width", 400 + 'px');
            $('#bgmodal').css("height", 300 + 'px');
            $('#bgmodal').css("z-index", 1001);
            // obtiendo tama�o de la ventana modal
            var wcnt = $('#bgmodal').width();
            var hcnt = $('#bgmodal').height();

            // obtener posicion central
            var mleft = (wscr - wcnt) / 2;
            var mtop = (hscr - hcnt) / 2;

            // estableciendo ventana modal en el centro
            $('#bgmodal').css("left", mleft + 'px');
            $('#bgmodal').css("top", mtop + 'px');
        }
    });

    return App.Controller.SelectionController;
}); 