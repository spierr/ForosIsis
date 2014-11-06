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

package co.edu.uniandes.csw.G3xtreme.responsable.logic.ejb;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;


import co.edu.uniandes.csw.G3xtreme.responsable.logic.dto.ResponsablePageDTO;
import co.edu.uniandes.csw.G3xtreme.responsable.logic.dto.ResponsableDTO;
import co.edu.uniandes.csw.G3xtreme.responsable.logic.api.IResponsableLogicService;
import co.edu.uniandes.csw.G3xtreme.responsable.persistence.ResponsablePersistence;
import co.edu.uniandes.csw.G3xtreme.responsable.persistence.api.IResponsablePersistence;
import co.edu.uniandes.csw.G3xtreme.responsable.persistence.entity.ResponsableEntity;
import co.edu.uniandes.csw.G3xtreme.responsable.persistence.converter.ResponsableConverter;
import static co.edu.uniandes.csw.G3xtreme.util._TestUtil.*;

@RunWith(Arquillian.class)
public class ResponsableLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(ResponsableLogicService.class.getPackage())
				.addPackage(IResponsableLogicService.class.getPackage())
				.addPackage(ResponsablePersistence.class.getPackage())
				.addPackage(ResponsableEntity.class.getPackage())
				.addPackage(IResponsablePersistence.class.getPackage())
                .addPackage(ResponsableDTO.class.getPackage())
                .addPackage(ResponsableConverter.class.getPackage())
                .addPackage(ResponsableEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IResponsableLogicService responsableLogicService;
	
	@Inject
	private IResponsablePersistence responsablePersistence;	

	@Before
	public void configTest() {
		try {
			clearData();
			insertData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearData() {
		List<ResponsableDTO> dtos=responsablePersistence.getResponsables();
		for(ResponsableDTO dto:dtos){
			responsablePersistence.deleteResponsable(dto.getId());
		}
	}

	private List<ResponsableDTO> data=new ArrayList<ResponsableDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			ResponsableDTO pdto=new ResponsableDTO();
			pdto.setName(generateRandom(String.class));
			pdto.setCorreo(generateRandom(String.class));
			pdto.setTipo(generateRandom(String.class));
			pdto=responsablePersistence.createResponsable(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createResponsableTest(){
		ResponsableDTO ldto=new ResponsableDTO();
		ldto.setName(generateRandom(String.class));
		ldto.setCorreo(generateRandom(String.class));
		ldto.setTipo(generateRandom(String.class));
		
		
		ResponsableDTO result=responsableLogicService.createResponsable(ldto);
		
		Assert.assertNotNull(result);
		
		ResponsableDTO pdto=responsablePersistence.getResponsable(result.getId());
		
		Assert.assertEquals(ldto.getName(), pdto.getName());	
		Assert.assertEquals(ldto.getCorreo(), pdto.getCorreo());	
		Assert.assertEquals(ldto.getTipo(), pdto.getTipo());	
	}
	
	@Test
	public void getResponsablesTest(){
		List<ResponsableDTO> list=responsableLogicService.getResponsables();
		Assert.assertEquals(list.size(), data.size());
        for(ResponsableDTO ldto:list){
        	boolean found=false;
            for(ResponsableDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getResponsableTest(){
		ResponsableDTO pdto=data.get(0);
		ResponsableDTO ldto=responsableLogicService.getResponsable(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getName(), ldto.getName());
		Assert.assertEquals(pdto.getCorreo(), ldto.getCorreo());
		Assert.assertEquals(pdto.getTipo(), ldto.getTipo());
        
	}
	
	@Test
	public void deleteResponsableTest(){
		ResponsableDTO pdto=data.get(0);
		responsableLogicService.deleteResponsable(pdto.getId());
        ResponsableDTO deleted=responsablePersistence.getResponsable(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateResponsableTest(){
		ResponsableDTO pdto=data.get(0);
		
		ResponsableDTO ldto=new ResponsableDTO();
		ldto.setId(pdto.getId());
		ldto.setName(generateRandom(String.class));
		ldto.setCorreo(generateRandom(String.class));
		ldto.setTipo(generateRandom(String.class));
		
		
		responsableLogicService.updateResponsable(ldto);
		
		
		ResponsableDTO resp=responsablePersistence.getResponsable(pdto.getId());
		
		Assert.assertEquals(ldto.getName(), resp.getName());	
		Assert.assertEquals(ldto.getCorreo(), resp.getCorreo());	
		Assert.assertEquals(ldto.getTipo(), resp.getTipo());	
	}
	
	@Test
	public void getResponsablePaginationTest(){
		
		ResponsablePageDTO dto1=responsableLogicService.getResponsables(1,2);
		Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(),2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(),3L);
		
		
		ResponsablePageDTO dto2=responsableLogicService.getResponsables(2,2);
		Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(),1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(),3L);
		
		for(ResponsableDTO dto:dto1.getRecords()){
        	boolean found=false;
            for(ResponsableDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        for(ResponsableDTO dto:dto2.getRecords()){
        	boolean found=false;
            for(ResponsableDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        ResponsablePageDTO dto3=responsableLogicService.getResponsables(1,3);
		Assert.assertNotNull(dto3);
        Assert.assertEquals(dto3.getRecords().size(),data.size());
        Assert.assertEquals(dto3.getTotalRecords().longValue(),data.size());
		
		for(ResponsableDTO dto:dto3.getRecords()){
        	boolean found=false;
            for(ResponsableDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	
}