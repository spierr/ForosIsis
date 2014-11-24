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

package co.edu.uniandes.csw.G3xtreme.lugar.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.uniandes.csw.G3xtreme.lugar.logic.dto.LugarDTO;
import co.edu.uniandes.csw.G3xtreme.lugar.logic.dto.LugarPageDTO;
import co.edu.uniandes.csw.G3xtreme.lugar.persistence.api._ILugarPersistence;
import co.edu.uniandes.csw.G3xtreme.lugar.persistence.converter.LugarConverter;
import co.edu.uniandes.csw.G3xtreme.lugar.persistence.entity.LugarEntity;

public abstract class _LugarPersistence implements _ILugarPersistence {

  	@PersistenceContext(unitName="ForosISISPU")
 
	protected EntityManager entityManager;
	
	public LugarDTO createLugar(LugarDTO lugar) {
		LugarEntity entity=LugarConverter.persistenceDTO2Entity(lugar);
		entityManager.persist(entity);
		return LugarConverter.entity2PersistenceDTO(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<LugarDTO> getLugars() {
		Query q = entityManager.createQuery("select u from LugarEntity u");
		return LugarConverter.entity2PersistenceDTOList(q.getResultList());
	}

	@SuppressWarnings("unchecked")
	public LugarPageDTO getLugars(Integer page, Integer maxRecords) {
		Query count = entityManager.createQuery("select count(u) from LugarEntity u");
		Long regCount = 0L;
		regCount = Long.parseLong(count.getSingleResult().toString());
		Query q = entityManager.createQuery("select u from LugarEntity u");
		if (page != null && maxRecords != null) {
		    q.setFirstResult((page-1)*maxRecords);
		    q.setMaxResults(maxRecords);
		}
		LugarPageDTO response = new LugarPageDTO();
		response.setTotalRecords(regCount);
		response.setRecords(LugarConverter.entity2PersistenceDTOList(q.getResultList()));
		return response;
	}

	public LugarDTO getLugar(Long id) {
		return LugarConverter.entity2PersistenceDTO(entityManager.find(LugarEntity.class, id));
	}

	public void deleteLugar(Long id) {
		LugarEntity entity=entityManager.find(LugarEntity.class, id);
		entityManager.remove(entity);
	}

	public void updateLugar(LugarDTO detail) {
		LugarEntity entity=entityManager.merge(LugarConverter.persistenceDTO2Entity(detail));
		LugarConverter.entity2PersistenceDTO(entity);
	}

}