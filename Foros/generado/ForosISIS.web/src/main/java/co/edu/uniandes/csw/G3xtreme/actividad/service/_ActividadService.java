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

package co.edu.uniandes.csw.G3xtreme.actividad.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.inject.Inject;

import co.edu.uniandes.csw.G3xtreme.actividad.logic.api.IActividadLogicService;
import co.edu.uniandes.csw.G3xtreme.actividad.logic.dto.ActividadDTO;
import co.edu.uniandes.csw.G3xtreme.actividad.logic.dto.ActividadPageDTO;


public abstract class _ActividadService {

	@Inject
	protected IActividadLogicService actividadLogicService;
	
	@POST
	public ActividadDTO createActividad(ActividadDTO actividad){
		return actividadLogicService.createActividad(actividad);
	}
	
	@DELETE
	@Path("{id}")
	public void deleteActividad(@PathParam("id") Long id){
		actividadLogicService.deleteActividad(id);
	}
	
	@GET
	public ActividadPageDTO getActividads(@QueryParam("page") Integer page, @QueryParam("maxRecords") Integer maxRecords){
		return actividadLogicService.getActividads(page, maxRecords);
	}
	
	@GET
	@Path("{id}")
	public ActividadDTO getActividad(@PathParam("id") Long id){
		return actividadLogicService.getActividad(id);
	}
	
	@PUT
	public void updateActividad(@PathParam("id") Long id, ActividadDTO actividad){
		actividadLogicService.updateActividad(actividad);
	}
	
}