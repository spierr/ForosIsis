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

package co.edu.uniandes.csw.G3xtreme.fase.master.logic.dto;

import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaDTO;
import co.edu.uniandes.csw.G3xtreme.fase.logic.dto.FaseDTO;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public abstract class _FaseMasterDTO {

 
    protected FaseDTO faseEntity;
    protected Long id;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public FaseDTO getFaseEntity() {
        return faseEntity;
    }

    public void setFaseEntity(FaseDTO faseEntity) {
        this.faseEntity = faseEntity;
    }
    
    public List<TareaDTO> createtarea_fase;
    public List<TareaDTO> updatetarea_fase;
    public List<TareaDTO> deletetarea_fase;
    public List<TareaDTO> listtarea_fase;	
	
	
	
    public List<TareaDTO> getCreatetarea_fase(){ return createtarea_fase; };
    public void setCreatetarea_fase(List<TareaDTO> createtarea_fase){ this.createtarea_fase=createtarea_fase; };
    public List<TareaDTO> getUpdatetarea_fase(){ return updatetarea_fase; };
    public void setUpdatetarea_fase(List<TareaDTO> updatetarea_fase){ this.updatetarea_fase=updatetarea_fase; };
    public List<TareaDTO> getDeletetarea_fase(){ return deletetarea_fase; };
    public void setDeletetarea_fase(List<TareaDTO> deletetarea_fase){ this.deletetarea_fase=deletetarea_fase; };
    public List<TareaDTO> getListtarea_fase(){ return listtarea_fase; };
    public void setListtarea_fase(List<TareaDTO> listtarea_fase){ this.listtarea_fase=listtarea_fase; };	
	
	
}

