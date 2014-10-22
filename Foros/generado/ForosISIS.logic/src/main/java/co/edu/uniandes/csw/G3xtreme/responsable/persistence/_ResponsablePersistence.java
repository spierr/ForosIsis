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


Source generated by CrudMaker version 1.0.0.201408112050

*/

package co.edu.uniandes.csw.G3xtreme.responsable.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.uniandes.csw.G3xtreme.responsable.logic.dto.ResponsableDTO;
import co.edu.uniandes.csw.G3xtreme.responsable.persistence.api._IResponsablePersistence;
import co.edu.uniandes.csw.G3xtreme.responsable.persistence.converter.ResponsableConverter;
import co.edu.uniandes.csw.G3xtreme.responsable.persistence.entity.ResponsableEntity;

public abstract class _ResponsablePersistence implements _IResponsablePersistence {

  	@PersistenceContext(unitName="ForosISISPU")
 
	protected EntityManager entityManager;
	
	public ResponsableDTO createResponsable(ResponsableDTO responsable) {
		ResponsableEntity entity=ResponsableConverter.persistenceDTO2Entity(responsable);
		entityManager.persist(entity);
		return ResponsableConverter.entity2PersistenceDTO(entity);
	}

	@SuppressWarnings("unchecked")
	public List<ResponsableDTO> getResponsables() {
		Query q = entityManager.createQuery("select u from ResponsableEntity u");
		return ResponsableConverter.entity2PersistenceDTOList(q.getResultList());
	}

	public ResponsableDTO getResponsable(Long id) {
		return ResponsableConverter.entity2PersistenceDTO(entityManager.find(ResponsableEntity.class, id));
	}

	public void deleteResponsable(Long id) {
		ResponsableEntity entity=entityManager.find(ResponsableEntity.class, id);
		entityManager.remove(entity);
	}

	public void updateResponsable(ResponsableDTO detail) {
		ResponsableEntity entity=entityManager.merge(ResponsableConverter.persistenceDTO2Entity(detail));
		ResponsableConverter.entity2PersistenceDTO(entity);
	}

}