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


Source generated by CrudMaker version 1.0.0.201410152247

*/

package co.edu.uniandes.csw.G3xtreme.tarea.service;

import co.edu.uniandes.csw.G3xtreme.info.logic.dto.InfoDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.api.ITareaLogicService;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaPageDTO;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    
    @Inject
    protected ITareaLogicService tareaLogicService;
    
    @Context
    UriInfo uriInfo;
        
    @GET
    @Path("/tareasByFase")
    public TareaPageDTO darTareasPorFase(@QueryParam("page") Integer page, @QueryParam("maxRecords") Integer maxRecords) {
        //Implementar l�gica de b�squeda
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String idFase = queryParams.getFirst("idFase");
        if (idFase  != null && !idFase.isEmpty()) {
            return tareaLogicService.getTareasByFase(idFase);
        }
        return super.getTareas(page, maxRecords);
    }
   
    //1
    @GET
    @Path("/getTareasForo")
    public TareaPageDTO getTareas(@QueryParam("page") Integer page, @QueryParam("maxRecords") Integer maxRecords) {
        //Implementar l�gica de b�squeda
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String responsable = queryParams.getFirst("id");
        if (responsable != null && !responsable.isEmpty()) {
            return tareaLogicService.getTareasAll(responsable);
        }
        return super.getTareas(page, maxRecords);
    }
    
    
    
    //2
    @GET
    @Path("/crearTarea")
    public TareaDTO crearTareaFaseForo(){
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        TareaDTO nueva = new TareaDTO();
        String descripcion = queryParams.getFirst("descripcion");
        String estado = queryParams.getFirst("estado");
        String fechaInic = queryParams.getFirst("fechaInicio");
        String fechaFin = queryParams.getFirst("fechaFin");
        //el id me imagino que se auto incrementa
        String nombre = queryParams.getFirst("name");
        String responsable = queryParams.getFirst("responsable");
        String responsableId = queryParams.getFirst("responsable_tareaId");
        String idFase = queryParams.getFirst("idFase");
        nueva.setDescripcion(descripcion);
        nueva.setEstado(Integer.parseInt(estado));
        nueva.setFechaInicio(fechaInic);
        nueva.setFechaFin(fechaFin);
        nueva.setName(nombre);
        nueva.setResponsable(responsable);
        nueva.setResponsable_tareaId(Long.parseLong(responsableId));
        
        
        
        //retornar en el InfoDTO si se pudo o no
        
        return tareaLogicService.crearTarea(nueva, idFase);
    }
    
    
    @GET
    @Path("/ResponTarea")
    public TareaPageDTO getTareasResponsable(@QueryParam("page") Integer page, @QueryParam("maxRecords") Integer maxRecords) {
        //Implementar l�gica de b�squeda
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String responsable = queryParams.getFirst("responsable");
        if (responsable != null && !responsable.isEmpty()) {
            return tareaLogicService.getTareasPorResponsable(responsable);
        }
        return super.getTareas(page, maxRecords);
    }
    
    @GET
    public InfoDTO updateEstadoTarea(@PathParam("id") long id){
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String idTarea = queryParams.getFirst("id_tarea");
        String valor = queryParams.getFirst("valor");
        return tareaLogicService.updateEstadoTarea(id,idTarea,valor);
    }

}
