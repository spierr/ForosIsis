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

package co.edu.uniandes.csw.G3xtreme.rol.logic.ejb;

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


import co.edu.uniandes.csw.G3xtreme.rol.logic.dto.RolPageDTO;
import co.edu.uniandes.csw.G3xtreme.rol.logic.dto.RolDTO;
import co.edu.uniandes.csw.G3xtreme.rol.logic.api.IRolLogicService;
import co.edu.uniandes.csw.G3xtreme.rol.persistence.RolPersistence;
import co.edu.uniandes.csw.G3xtreme.rol.persistence.api.IRolPersistence;
import co.edu.uniandes.csw.G3xtreme.rol.persistence.entity.RolEntity;
import co.edu.uniandes.csw.G3xtreme.rol.persistence.converter.RolConverter;
import static co.edu.uniandes.csw.G3xtreme.util._TestUtil.*;

@RunWith(Arquillian.class)
public class RolLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(RolLogicService.class.getPackage())
				.addPackage(IRolLogicService.class.getPackage())
				.addPackage(RolPersistence.class.getPackage())
				.addPackage(RolEntity.class.getPackage())
				.addPackage(IRolPersistence.class.getPackage())
                .addPackage(RolDTO.class.getPackage())
                .addPackage(RolConverter.class.getPackage())
                .addPackage(RolEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IRolLogicService rolLogicService;
	
	@Inject
	private IRolPersistence rolPersistence;	

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
		List<RolDTO> dtos=rolPersistence.getRols();
		for(RolDTO dto:dtos){
			rolPersistence.deleteRol(dto.getId());
		}
	}

	private List<RolDTO> data=new ArrayList<RolDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			RolDTO pdto=new RolDTO();
			pdto.setName(generateRandom(String.class));
			pdto=rolPersistence.createRol(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createRolTest(){
		RolDTO ldto=new RolDTO();
		ldto.setName(generateRandom(String.class));
		
		
		RolDTO result=rolLogicService.createRol(ldto);
		
		Assert.assertNotNull(result);
		
		RolDTO pdto=rolPersistence.getRol(result.getId());
		
		Assert.assertEquals(ldto.getName(), pdto.getName());	
	}
	
	@Test
	public void getRolsTest(){
		List<RolDTO> list=rolLogicService.getRols();
		Assert.assertEquals(list.size(), data.size());
        for(RolDTO ldto:list){
        	boolean found=false;
            for(RolDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getRolTest(){
		RolDTO pdto=data.get(0);
		RolDTO ldto=rolLogicService.getRol(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getName(), ldto.getName());
        
	}
	
	@Test
	public void deleteRolTest(){
		RolDTO pdto=data.get(0);
		rolLogicService.deleteRol(pdto.getId());
        RolDTO deleted=rolPersistence.getRol(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateRolTest(){
		RolDTO pdto=data.get(0);
		
		RolDTO ldto=new RolDTO();
		ldto.setId(pdto.getId());
		ldto.setName(generateRandom(String.class));
		
		
		rolLogicService.updateRol(ldto);
		
		
		RolDTO resp=rolPersistence.getRol(pdto.getId());
		
		Assert.assertEquals(ldto.getName(), resp.getName());	
	}
	
	@Test
	public void getRolPaginationTest(){
		
		RolPageDTO dto1=rolLogicService.getRols(1,2);
		Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(),2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(),3L);
		
		
		RolPageDTO dto2=rolLogicService.getRols(2,2);
		Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(),1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(),3L);
		
		for(RolDTO dto:dto1.getRecords()){
        	boolean found=false;
            for(RolDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        for(RolDTO dto:dto2.getRecords()){
        	boolean found=false;
            for(RolDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        RolPageDTO dto3=rolLogicService.getRols(1,3);
		Assert.assertNotNull(dto3);
        Assert.assertEquals(dto3.getRecords().size(),data.size());
        Assert.assertEquals(dto3.getTotalRecords().longValue(),data.size());
		
		for(RolDTO dto:dto3.getRecords()){
        	boolean found=false;
            for(RolDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	
}