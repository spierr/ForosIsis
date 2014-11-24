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

package co.edu.uniandes.csw.G3xtreme.dia_inscripcion.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.inject.Inject;

import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.logic.api.IDia_InscripcionLogicService;
import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.logic.dto.Dia_InscripcionDTO;
import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.logic.dto.Dia_InscripcionPageDTO;


public abstract class _Dia_InscripcionService {

	@Inject
	protected IDia_InscripcionLogicService dia_InscripcionLogicService;
	
	@POST
	public Dia_InscripcionDTO createDia_Inscripcion(Dia_InscripcionDTO dia_Inscripcion){
		return dia_InscripcionLogicService.createDia_Inscripcion(dia_Inscripcion);
	}
	
	@DELETE
	@Path("{id}")
	public void deleteDia_Inscripcion(@PathParam("id") Long id){
		dia_InscripcionLogicService.deleteDia_Inscripcion(id);
	}
	
	@GET
	public Dia_InscripcionPageDTO getDia_Inscripcions(@QueryParam("page") Integer page, @QueryParam("maxRecords") Integer maxRecords){
		return dia_InscripcionLogicService.getDia_Inscripcions(page, maxRecords);
	}
	
	@GET
	@Path("{id}")
	public Dia_InscripcionDTO getDia_Inscripcion(@PathParam("id") Long id){
		return dia_InscripcionLogicService.getDia_Inscripcion(id);
	}
	
	@PUT
	public void updateDia_Inscripcion(@PathParam("id") Long id, Dia_InscripcionDTO dia_Inscripcion){
		dia_InscripcionLogicService.updateDia_Inscripcion(dia_Inscripcion);
	}
	
}