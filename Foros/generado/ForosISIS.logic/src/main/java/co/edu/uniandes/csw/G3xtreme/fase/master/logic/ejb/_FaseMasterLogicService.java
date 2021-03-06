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

package co.edu.uniandes.csw.G3xtreme.fase.master.logic.ejb;

import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.persistence.api.ITareaPersistence;
import co.edu.uniandes.csw.G3xtreme.fase.logic.dto.FaseDTO;
import co.edu.uniandes.csw.G3xtreme.fase.master.logic.api._IFaseMasterLogicService;
import co.edu.uniandes.csw.G3xtreme.fase.master.logic.dto.FaseMasterDTO;
import co.edu.uniandes.csw.G3xtreme.fase.master.persistence.api.IFaseMasterPersistence;
import co.edu.uniandes.csw.G3xtreme.fase.master.persistence.entity.Fasetarea_faseEntity;
import co.edu.uniandes.csw.G3xtreme.fase.persistence.api.IFasePersistence;
import javax.inject.Inject;

public abstract class _FaseMasterLogicService implements _IFaseMasterLogicService {

    @Inject
    protected IFasePersistence fasePersistance;
    @Inject
    protected IFaseMasterPersistence faseMasterPersistance;
    @Inject
    protected ITareaPersistence tareaPersistance;

    public FaseMasterDTO createMasterFase(FaseMasterDTO fase) {
        FaseDTO persistedFaseDTO = fasePersistance.createFase(fase.getFaseEntity());
        if (fase.getCreatetarea_fase() != null) {
            for (TareaDTO tareaDTO : fase.getCreatetarea_fase()) {
                TareaDTO createdTareaDTO = tareaPersistance.createTarea(tareaDTO);
                Fasetarea_faseEntity faseTareaEntity = new Fasetarea_faseEntity(persistedFaseDTO.getId(), createdTareaDTO.getId());
                faseMasterPersistance.createFasetarea_faseEntity(faseTareaEntity);
            }
        }
        // update tarea
        if (fase.getUpdatetarea_fase() != null) {
            for (TareaDTO tareaDTO : fase.getUpdatetarea_fase()) {
                tareaPersistance.updateTarea(tareaDTO);
                Fasetarea_faseEntity faseTareaEntity = new Fasetarea_faseEntity(persistedFaseDTO.getId(), tareaDTO.getId());
                faseMasterPersistance.createFasetarea_faseEntity(faseTareaEntity);
            }
        }
        return fase;
    }

    public FaseMasterDTO getMasterFase(Long id) {
        return faseMasterPersistance.getFase(id);
    }

    public void deleteMasterFase(Long id) {
        fasePersistance.deleteFase(id);
    }

    public void updateMasterFase(FaseMasterDTO fase) {
        fasePersistance.updateFase(fase.getFaseEntity());

        //---- FOR RELATIONSHIP
        // persist new tarea
        if (fase.getCreatetarea_fase() != null) {
            for (TareaDTO tareaDTO : fase.getCreatetarea_fase()) {
                TareaDTO createdTareaDTO = tareaPersistance.createTarea(tareaDTO);
                Fasetarea_faseEntity faseTareaEntity = new Fasetarea_faseEntity(fase.getFaseEntity().getId(), createdTareaDTO.getId());
                faseMasterPersistance.createFasetarea_faseEntity(faseTareaEntity);
            }
        }
        // update tarea
        if (fase.getUpdatetarea_fase() != null) {
            for (TareaDTO tareaDTO : fase.getUpdatetarea_fase()) {
                tareaPersistance.updateTarea(tareaDTO);
            }
        }
        // delete tarea
        if (fase.getDeletetarea_fase() != null) {
            for (TareaDTO tareaDTO : fase.getDeletetarea_fase()) {
                faseMasterPersistance.deleteFasetarea_faseEntity(fase.getFaseEntity().getId(), tareaDTO.getId());
                tareaPersistance.deleteTarea(tareaDTO.getId());
            }
        }
    }
}
