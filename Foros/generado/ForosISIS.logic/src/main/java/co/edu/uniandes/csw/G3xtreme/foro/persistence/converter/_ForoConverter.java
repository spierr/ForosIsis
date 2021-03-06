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

package co.edu.uniandes.csw.G3xtreme.foro.persistence.converter;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;


import co.edu.uniandes.csw.G3xtreme.foro.logic.dto.ForoDTO;
import co.edu.uniandes.csw.G3xtreme.foro.persistence.entity.ForoEntity;

public abstract class _ForoConverter {

	public static ForoDTO entity2PersistenceDTO(ForoEntity entity){
		if (entity != null) {
			ForoDTO dto = new ForoDTO();
					dto.setId(entity.getId());
					dto.setName(entity.getName());
					dto.setTema(entity.getTema());
					dto.setPresupuesto(entity.getPresupuesto());
			return dto;
		}else{
			return null;
		}
	}
	
	public static ForoEntity persistenceDTO2Entity(ForoDTO dto){
		if(dto!=null){
			ForoEntity entity=new ForoEntity();
					entity.setId(dto.getId());
			
					entity.setName(dto.getName());
			
					entity.setTema(dto.getTema());
			
					entity.setPresupuesto(dto.getPresupuesto());
			
			return entity;
		}else {
			return null;
		}
	}
	
	public static List<ForoDTO> entity2PersistenceDTOList(List<ForoEntity> entities){
		List<ForoDTO> dtos=new ArrayList<ForoDTO>();
		for(ForoEntity entity:entities){
			dtos.add(entity2PersistenceDTO(entity));
		}
		return dtos;
	}
	
	public static List<ForoEntity> persistenceDTO2EntityList(List<ForoDTO> dtos){
		List<ForoEntity> entities=new ArrayList<ForoEntity>();
		for(ForoDTO dto:dtos){
			entities.add(persistenceDTO2Entity(dto));
		}
		return entities;
	}
}