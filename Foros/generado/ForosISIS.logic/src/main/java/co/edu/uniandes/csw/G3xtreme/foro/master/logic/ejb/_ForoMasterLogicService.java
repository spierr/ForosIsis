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

package co.edu.uniandes.csw.G3xtreme.foro.master.logic.ejb;

import co.edu.uniandes.csw.G3xtreme.fase.logic.dto.FaseDTO;
import co.edu.uniandes.csw.G3xtreme.fase.persistence.api.IFasePersistence;
import co.edu.uniandes.csw.G3xtreme.actividad.logic.dto.ActividadDTO;
import co.edu.uniandes.csw.G3xtreme.actividad.persistence.api.IActividadPersistence;
import co.edu.uniandes.csw.G3xtreme.foro.logic.dto.ForoDTO;
import co.edu.uniandes.csw.G3xtreme.foro.master.logic.api._IForoMasterLogicService;
import co.edu.uniandes.csw.G3xtreme.foro.master.logic.dto.ForoMasterDTO;
import co.edu.uniandes.csw.G3xtreme.foro.master.persistence.api.IForoMasterPersistence;
import co.edu.uniandes.csw.G3xtreme.foro.master.persistence.entity.Forofase_foroEntity;
import co.edu.uniandes.csw.G3xtreme.foro.master.persistence.entity.ForoactividadEntity;
import co.edu.uniandes.csw.G3xtreme.foro.persistence.api.IForoPersistence;
import javax.inject.Inject;

public abstract class _ForoMasterLogicService implements _IForoMasterLogicService {

    @Inject
    protected IForoPersistence foroPersistance;
    @Inject
    protected IForoMasterPersistence foroMasterPersistance;
    @Inject
    protected IFasePersistence fasePersistance;
    @Inject
    protected IActividadPersistence actividadPersistance;

    public ForoMasterDTO createMasterForo(ForoMasterDTO foro) {
        ForoDTO persistedForoDTO = foroPersistance.createForo(foro.getForoEntity());
        if (foro.getCreatefase_foro() != null) {
            for (FaseDTO faseDTO : foro.getCreatefase_foro()) {
                FaseDTO createdFaseDTO = fasePersistance.createFase(faseDTO);
                Forofase_foroEntity foroFaseEntity = new Forofase_foroEntity(persistedForoDTO.getId(), createdFaseDTO.getId());
                foroMasterPersistance.createForofase_foroEntity(foroFaseEntity);
            }
        }
        if (foro.getCreateactividad() != null) {
            for (ActividadDTO actividadDTO : foro.getCreateactividad()) {
                ActividadDTO createdActividadDTO = actividadPersistance.createActividad(actividadDTO);
                ForoactividadEntity foroActividadEntity = new ForoactividadEntity(persistedForoDTO.getId(), createdActividadDTO.getId());
                foroMasterPersistance.createForoactividadEntity(foroActividadEntity);
            }
        }
        // update fase
        if (foro.getUpdatefase_foro() != null) {
            for (FaseDTO faseDTO : foro.getUpdatefase_foro()) {
                fasePersistance.updateFase(faseDTO);
                Forofase_foroEntity foroFaseEntity = new Forofase_foroEntity(persistedForoDTO.getId(), faseDTO.getId());
                foroMasterPersistance.createForofase_foroEntity(foroFaseEntity);
            }
        }
        // update actividad
        if (foro.getUpdateactividad() != null) {
            for (ActividadDTO actividadDTO : foro.getUpdateactividad()) {
                actividadPersistance.updateActividad(actividadDTO);
                ForoactividadEntity foroActividadEntity = new ForoactividadEntity(persistedForoDTO.getId(), actividadDTO.getId());
                foroMasterPersistance.createForoactividadEntity(foroActividadEntity);
            }
        }
        return foro;
    }

    public ForoMasterDTO getMasterForo(Long id) {
        return foroMasterPersistance.getForo(id);
    }

    public void deleteMasterForo(Long id) {
        foroPersistance.deleteForo(id);
    }

    public void updateMasterForo(ForoMasterDTO foro) {
        foroPersistance.updateForo(foro.getForoEntity());

        //---- FOR RELATIONSHIP
        // persist new fase
        if (foro.getCreatefase_foro() != null) {
            for (FaseDTO faseDTO : foro.getCreatefase_foro()) {
                FaseDTO createdFaseDTO = fasePersistance.createFase(faseDTO);
                Forofase_foroEntity foroFaseEntity = new Forofase_foroEntity(foro.getForoEntity().getId(), createdFaseDTO.getId());
                foroMasterPersistance.createForofase_foroEntity(foroFaseEntity);
            }
        }
        // update fase
        if (foro.getUpdatefase_foro() != null) {
            for (FaseDTO faseDTO : foro.getUpdatefase_foro()) {
                fasePersistance.updateFase(faseDTO);
            }
        }
        // delete fase
        if (foro.getDeletefase_foro() != null) {
            for (FaseDTO faseDTO : foro.getDeletefase_foro()) {
                foroMasterPersistance.deleteForofase_foroEntity(foro.getForoEntity().getId(), faseDTO.getId());
                fasePersistance.deleteFase(faseDTO.getId());
            }
        }
        // persist new actividad
        if (foro.getCreateactividad() != null) {
            for (ActividadDTO actividadDTO : foro.getCreateactividad()) {
                ActividadDTO createdActividadDTO = actividadPersistance.createActividad(actividadDTO);
                ForoactividadEntity foroActividadEntity = new ForoactividadEntity(foro.getForoEntity().getId(), createdActividadDTO.getId());
                foroMasterPersistance.createForoactividadEntity(foroActividadEntity);
            }
        }
        // update actividad
        if (foro.getUpdateactividad() != null) {
            for (ActividadDTO actividadDTO : foro.getUpdateactividad()) {
                actividadPersistance.updateActividad(actividadDTO);
            }
        }
        // delete actividad
        if (foro.getDeleteactividad() != null) {
            for (ActividadDTO actividadDTO : foro.getDeleteactividad()) {
                foroMasterPersistance.deleteForoactividadEntity(foro.getForoEntity().getId(), actividadDTO.getId());
                actividadPersistance.deleteActividad(actividadDTO.getId());
            }
        }
    }
}
