define([], function() {
    App.Model.CacheModel = {
        fetch: function() {
            console.log('Should not be invoke');
        },
        save: function(p, options) {
            if(this.validate){
                var val=this.validate(this.attributes,options);
                if(val && val!=''){
                    this.trigger('invalid', {event: 'validation', validationError: val});
                    return;
                }
            }
            // Negative integer refer to models that are new in the list
            if (!this.get('id')) {
                this.set('id', -App.Utils.randomInteger());
                this.created = true;
            }else if(this.get('id')>0){
                this.updated = true;                
            }
            this.cacheList.set([this], {remove: false});
            if (options.success) {
                options.success();
            }
        },
        setCacheList: function(list) {
            this.cacheList = list;
        },
        destroy: function(options) {
            if(!options){
                options = {};
            }
            if (this.get('id') < 0) {
                options.addToDeleteList = false;
            } else {
                this.isDeleted = true;
                options.addToDeleteList = true;
            }
            this.cacheList.remove(this, options);
            if (options.success) {
                options.success();
            }
        },
        isCreated: function() { 
            return this.created;
        },
        isUpdated: function() {
            return this.updated;
        }
    };

    App.Model.CacheListModel = {
        fetch: function(options) {
            if (!this.cacheListIsSet) {
                for (var i = 0; i < this.cacheModels.length; i++) {
                    this.add(this.cacheModels[i]);
                }
                this.cacheListIsSet = true;
            }
            if (options.success) {
            	var resp = {
                    state: {
                        currentPage: 1,
                        totalPages: 1,
                        totalRecords: this.cacheModels.length
                    }
                };
                options.success(resp);
            }
        },
        get: function(id) {
            var obj = Backbone.Collection.prototype.get.call(this, id);
            if (obj) {
                obj.setCacheList(this);
            }
            return obj;
        },
        remove: function(model, options) {
            if (options.addToDeleteList) {
                this.deletedModels.push(model);
            }
            Backbone.Collection.prototype.remove.call(this, model, options);
        },
		reset: function(params){
			Backbone.Collection.prototype.reset.call(this, params);
			this.deletedModels = [];
		}
    };

});

