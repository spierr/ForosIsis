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

package co.edu.uniandes.csw.G3xtreme.tarea.service;

import co.edu.uniandes.csw.G3xtreme.fase.logic.dto.FaseDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaPageDTO;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

@Path("/Tarea")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TareaService extends _TareaService {
@Context
    UriInfo uriInfo;
    
        @GET
        @Path("/darTareasResponsable")
        public TareaPageDTO darTareasResponsable(@QueryParam("page") Integer page, @QueryParam("maxRecords") Integer maxRecords){
	 MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
            String idresp = queryParams.getFirst("idResponsable");	
            return tareaLogicService.darTareasResponsable(page, maxRecords,"1");
	}
        @GET
        @Path("/buscarTareasNombre")
        public TareaPageDTO darTareasPorNombre(@QueryParam("page") Integer page, @QueryParam("maxRecords") Integer maxRecords) {
        //Implementar l�gica de b�squeda
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String name = queryParams.getFirst("name");
        if (name != null && !name.isEmpty()) {
            return tareaLogicService.darTareasPorNombre(name);
        }
        return super.getTareas(page, maxRecords);
     }
        
     public void cambiarEstadoTarea(long idTarea, Integer nuevoEstado) {
        tareaLogicService.getTarea(idTarea).setEstado(nuevoEstado);
        tareaLogicService.updateTarea(tareaLogicService.getTarea(idTarea));
         
        } 
}