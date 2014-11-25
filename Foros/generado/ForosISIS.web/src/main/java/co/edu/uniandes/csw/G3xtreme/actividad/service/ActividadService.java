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

package co.edu.uniandes.csw.G3xtreme.actividad.service;

import co.edu.uniandes.csw.G3xtreme.actividad.logic.dto.ActividadDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaPageDTO;
import java.util.ArrayList;
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

@Path("/Actividad")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActividadService extends _ActividadService {
    @Context
    UriInfo uriInfo;
    
        @GET
        @Path("/darActividadesExpositor")
        public List<ActividadDTO> darTareasResponsable(){
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
            String idexpositor = queryParams.getFirst("idExpositor");	
            List<ActividadDTO>todas=actividadLogicService.getActividads();
            List<ActividadDTO> resp= new ArrayList<ActividadDTO>();
            for (int i = 0; i < todas.size(); i++) {
                ActividadDTO actual= todas.get(i);
                if(actual.getExpositor_actividadId()==Long.parseLong(idexpositor))
                    resp.add(actual);
            }
            return resp;
	}

}