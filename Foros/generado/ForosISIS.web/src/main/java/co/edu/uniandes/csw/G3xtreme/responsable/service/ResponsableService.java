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

package co.edu.uniandes.csw.G3xtreme.responsable.service;

import co.edu.uniandes.csw.G3xtreme.info.dto.InfoDTO;
import co.edu.uniandes.csw.G3xtreme.responsable.logic.dto.ResponsableDTO;
import co.edu.uniandes.csw.G3xtreme.responsable.logic.dto.ResponsablePageDTO;
import co.edu.uniandes.csw.G3xtreme.responsable.logic.ejb.ResponsableLogicService;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

@Path("/Responsable")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResponsableService extends _ResponsableService {
    
    @Context
    UriInfo uriInfo;
    
    @GET
    @Path("/enviarCorreo")
    public InfoDTO enviarCorreoAResponsable(@PathParam("id") long id)
    {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String idResp = queryParams.getFirst("idResponsable");
        String mensaje = queryParams.getFirst("mensaje");
        System.out.println("Esos son:" + idResp + "-" + mensaje);
         InfoDTO a = new InfoDTO();
         try {
           boolean resp= responsableLogicService.enviarCorreoAResponsable(mensaje, idResp);
           a.setExito("Se envi� con �xito el mensaje.");
           a.setError(!resp);
        } catch (Exception e) {
           a.setError("Error enviando el mensaje");
           a.setError(true);
        }
         return a;
    }
    
    @GET
    @Path("services/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getResponsableId(@PathParam("name") String name) {
        return "" + responsableLogicService.getResponsableId(name).getId();
    }
    
    @GET
    @Path("/buscarResponsablesNombre")
        public ResponsablePageDTO getResponsablesPorNombre(@QueryParam("page") Integer page, @QueryParam("maxRecords") Integer maxRecords) {
        //Implementar l�gica de b�squeda
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String name = queryParams.getFirst("name");
        if (name != null && !name.isEmpty()) {
            return responsableLogicService.getResponsablesPorNombre(name); 
        }
        return super.getResponsables(page, maxRecords);
    }
 
    /**
     * Servicio que retorna un cliente dado su nombre
     *
     * @param name el nombre del cliente
     * @return json con el cliente
     */
    @GET
    @Path("/darResponsablePOrNombreExacto")
    public ResponsableDTO getResponsablebyName(@PathParam("name") String name) {
        return responsableLogicService.getResponsableByName(name);
    }
}