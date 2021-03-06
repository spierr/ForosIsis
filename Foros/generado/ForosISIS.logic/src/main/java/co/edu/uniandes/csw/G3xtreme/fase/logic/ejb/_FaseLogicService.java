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

package co.edu.uniandes.csw.G3xtreme.fase.logic.ejb;
import java.util.List;
import javax.inject.Inject;

import co.edu.uniandes.csw.G3xtreme.fase.logic.dto.FaseDTO;
import co.edu.uniandes.csw.G3xtreme.fase.logic.dto.FasePageDTO;
import co.edu.uniandes.csw.G3xtreme.fase.logic.api._IFaseLogicService;
import co.edu.uniandes.csw.G3xtreme.fase.persistence.api.IFasePersistence;

public abstract class _FaseLogicService implements _IFaseLogicService {

	@Inject
	protected IFasePersistence persistance;

	public FaseDTO createFase(FaseDTO fase){
		return persistance.createFase( fase); 
    }

	public List<FaseDTO> getFases(){
		return persistance.getFases(); 
	}

	public FasePageDTO getFases(Integer page, Integer maxRecords){
		return persistance.getFases(page, maxRecords); 
	}

	public FaseDTO getFase(Long id){
		return persistance.getFase(id); 
	}

	public void deleteFase(Long id){
	    persistance.deleteFase(id); 
	}

	public void updateFase(FaseDTO fase){
	    persistance.updateFase(fase); 
	}	
}