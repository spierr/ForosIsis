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

package co.edu.uniandes.csw.G3xtreme.foro.logic.ejb;

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


import co.edu.uniandes.csw.G3xtreme.foro.logic.dto.ForoPageDTO;
import co.edu.uniandes.csw.G3xtreme.foro.logic.dto.ForoDTO;
import co.edu.uniandes.csw.G3xtreme.foro.logic.api.IForoLogicService;
import co.edu.uniandes.csw.G3xtreme.foro.persistence.ForoPersistence;
import co.edu.uniandes.csw.G3xtreme.foro.persistence.api.IForoPersistence;
import co.edu.uniandes.csw.G3xtreme.foro.persistence.entity.ForoEntity;
import co.edu.uniandes.csw.G3xtreme.foro.persistence.converter.ForoConverter;
import static co.edu.uniandes.csw.G3xtreme.util._TestUtil.*;

@RunWith(Arquillian.class)
public class ForoLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(ForoLogicService.class.getPackage())
				.addPackage(IForoLogicService.class.getPackage())
				.addPackage(ForoPersistence.class.getPackage())
				.addPackage(ForoEntity.class.getPackage())
				.addPackage(IForoPersistence.class.getPackage())
                .addPackage(ForoDTO.class.getPackage())
                .addPackage(ForoConverter.class.getPackage())
                .addPackage(ForoEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IForoLogicService foroLogicService;
	
	@Inject
	private IForoPersistence foroPersistence;	

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
		List<ForoDTO> dtos=foroPersistence.getForos();
		for(ForoDTO dto:dtos){
			foroPersistence.deleteForo(dto.getId());
		}
	}

	private List<ForoDTO> data=new ArrayList<ForoDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			ForoDTO pdto=new ForoDTO();
			pdto.setName(generateRandom(String.class));
			pdto.setTema(generateRandom(String.class));
			pdto.setPresupuesto(generateRandom(Long.class));
			pdto=foroPersistence.createForo(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createForoTest(){
		ForoDTO ldto=new ForoDTO();
		ldto.setName(generateRandom(String.class));
		ldto.setTema(generateRandom(String.class));
		ldto.setPresupuesto(generateRandom(Long.class));
		
		
		ForoDTO result=foroLogicService.createForo(ldto);
		
		Assert.assertNotNull(result);
		
		ForoDTO pdto=foroPersistence.getForo(result.getId());
		
		Assert.assertEquals(ldto.getName(), pdto.getName());	
		Assert.assertEquals(ldto.getTema(), pdto.getTema());	
		Assert.assertEquals(ldto.getPresupuesto(), pdto.getPresupuesto());	
	}
	
	@Test
	public void getForosTest(){
		List<ForoDTO> list=foroLogicService.getForos();
		Assert.assertEquals(list.size(), data.size());
        for(ForoDTO ldto:list){
        	boolean found=false;
            for(ForoDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getForoTest(){
		ForoDTO pdto=data.get(0);
		ForoDTO ldto=foroLogicService.getForo(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getName(), ldto.getName());
		Assert.assertEquals(pdto.getTema(), ldto.getTema());
		Assert.assertEquals(pdto.getPresupuesto(), ldto.getPresupuesto());
        
	}
	
	@Test
	public void deleteForoTest(){
		ForoDTO pdto=data.get(0);
		foroLogicService.deleteForo(pdto.getId());
        ForoDTO deleted=foroPersistence.getForo(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateForoTest(){
		ForoDTO pdto=data.get(0);
		
		ForoDTO ldto=new ForoDTO();
		ldto.setId(pdto.getId());
		ldto.setName(generateRandom(String.class));
		ldto.setTema(generateRandom(String.class));
		ldto.setPresupuesto(generateRandom(Long.class));
		
		
		foroLogicService.updateForo(ldto);
		
		
		ForoDTO resp=foroPersistence.getForo(pdto.getId());
		
		Assert.assertEquals(ldto.getName(), resp.getName());	
		Assert.assertEquals(ldto.getTema(), resp.getTema());	
		Assert.assertEquals(ldto.getPresupuesto(), resp.getPresupuesto());	
	}
	
	@Test
	public void getForoPaginationTest(){
		
		ForoPageDTO dto1=foroLogicService.getForos(1,2);
		Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(),2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(),3L);
		
		
		ForoPageDTO dto2=foroLogicService.getForos(2,2);
		Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(),1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(),3L);
		
		for(ForoDTO dto:dto1.getRecords()){
        	boolean found=false;
            for(ForoDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        for(ForoDTO dto:dto2.getRecords()){
        	boolean found=false;
            for(ForoDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        ForoPageDTO dto3=foroLogicService.getForos(1,3);
		Assert.assertNotNull(dto3);
        Assert.assertEquals(dto3.getRecords().size(),data.size());
        Assert.assertEquals(dto3.getTotalRecords().longValue(),data.size());
		
		for(ForoDTO dto:dto3.getRecords()){
        	boolean found=false;
            for(ForoDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	
}