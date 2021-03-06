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

package co.edu.uniandes.csw.G3xtreme.empresa.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.uniandes.csw.G3xtreme.empresa.logic.dto.EmpresaDTO;
import co.edu.uniandes.csw.G3xtreme.empresa.logic.dto.EmpresaPageDTO;
import co.edu.uniandes.csw.G3xtreme.empresa.persistence.api._IEmpresaPersistence;
import co.edu.uniandes.csw.G3xtreme.empresa.persistence.converter.EmpresaConverter;
import co.edu.uniandes.csw.G3xtreme.empresa.persistence.entity.EmpresaEntity;

public abstract class _EmpresaPersistence implements _IEmpresaPersistence {

  	@PersistenceContext(unitName="ForosISISPU")
 
	protected EntityManager entityManager;
	
	public EmpresaDTO createEmpresa(EmpresaDTO empresa) {
		EmpresaEntity entity=EmpresaConverter.persistenceDTO2Entity(empresa);
		entityManager.persist(entity);
		return EmpresaConverter.entity2PersistenceDTO(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<EmpresaDTO> getEmpresas() {
		Query q = entityManager.createQuery("select u from EmpresaEntity u");
		return EmpresaConverter.entity2PersistenceDTOList(q.getResultList());
	}

	@SuppressWarnings("unchecked")
	public EmpresaPageDTO getEmpresas(Integer page, Integer maxRecords) {
		Query count = entityManager.createQuery("select count(u) from EmpresaEntity u");
		Long regCount = 0L;
		regCount = Long.parseLong(count.getSingleResult().toString());
		Query q = entityManager.createQuery("select u from EmpresaEntity u");
		if (page != null && maxRecords != null) {
		    q.setFirstResult((page-1)*maxRecords);
		    q.setMaxResults(maxRecords);
		}
		EmpresaPageDTO response = new EmpresaPageDTO();
		response.setTotalRecords(regCount);
		response.setRecords(EmpresaConverter.entity2PersistenceDTOList(q.getResultList()));
		return response;
	}

	public EmpresaDTO getEmpresa(Long id) {
		return EmpresaConverter.entity2PersistenceDTO(entityManager.find(EmpresaEntity.class, id));
	}

	public void deleteEmpresa(Long id) {
		EmpresaEntity entity=entityManager.find(EmpresaEntity.class, id);
		entityManager.remove(entity);
	}

	public void updateEmpresa(EmpresaDTO detail) {
		EmpresaEntity entity=entityManager.merge(EmpresaConverter.persistenceDTO2Entity(detail));
		EmpresaConverter.entity2PersistenceDTO(entity);
	}

}