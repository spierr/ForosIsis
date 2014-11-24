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

package co.edu.uniandes.csw.G3xtreme.lugar.logic.ejb;

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


import co.edu.uniandes.csw.G3xtreme.lugar.logic.dto.LugarPageDTO;
import co.edu.uniandes.csw.G3xtreme.lugar.logic.dto.LugarDTO;
import co.edu.uniandes.csw.G3xtreme.lugar.logic.api.ILugarLogicService;
import co.edu.uniandes.csw.G3xtreme.lugar.persistence.LugarPersistence;
import co.edu.uniandes.csw.G3xtreme.lugar.persistence.api.ILugarPersistence;
import co.edu.uniandes.csw.G3xtreme.lugar.persistence.entity.LugarEntity;
import co.edu.uniandes.csw.G3xtreme.lugar.persistence.converter.LugarConverter;
import static co.edu.uniandes.csw.G3xtreme.util._TestUtil.*;

@RunWith(Arquillian.class)
public class LugarLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(LugarLogicService.class.getPackage())
				.addPackage(ILugarLogicService.class.getPackage())
				.addPackage(LugarPersistence.class.getPackage())
				.addPackage(LugarEntity.class.getPackage())
				.addPackage(ILugarPersistence.class.getPackage())
                .addPackage(LugarDTO.class.getPackage())
                .addPackage(LugarConverter.class.getPackage())
                .addPackage(LugarEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private ILugarLogicService lugarLogicService;
	
	@Inject
	private ILugarPersistence lugarPersistence;	

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
		List<LugarDTO> dtos=lugarPersistence.getLugars();
		for(LugarDTO dto:dtos){
			lugarPersistence.deleteLugar(dto.getId());
		}
	}

	private List<LugarDTO> data=new ArrayList<LugarDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			LugarDTO pdto=new LugarDTO();
			pdto.setName(generateRandom(String.class));
			pdto.setDireccion(generateRandom(String.class));
			pdto.setCosto(generateRandom(Long.class));
			pdto.setDetalles(generateRandom(String.class));
			pdto=lugarPersistence.createLugar(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createLugarTest(){
		LugarDTO ldto=new LugarDTO();
		ldto.setName(generateRandom(String.class));
		ldto.setDireccion(generateRandom(String.class));
		ldto.setCosto(generateRandom(Long.class));
		ldto.setDetalles(generateRandom(String.class));
		
		
		LugarDTO result=lugarLogicService.createLugar(ldto);
		
		Assert.assertNotNull(result);
		
		LugarDTO pdto=lugarPersistence.getLugar(result.getId());
		
		Assert.assertEquals(ldto.getName(), pdto.getName());	
		Assert.assertEquals(ldto.getDireccion(), pdto.getDireccion());	
		Assert.assertEquals(ldto.getCosto(), pdto.getCosto());	
		Assert.assertEquals(ldto.getDetalles(), pdto.getDetalles());	
	}
	
	@Test
	public void getLugarsTest(){
		List<LugarDTO> list=lugarLogicService.getLugars();
		Assert.assertEquals(list.size(), data.size());
        for(LugarDTO ldto:list){
        	boolean found=false;
            for(LugarDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getLugarTest(){
		LugarDTO pdto=data.get(0);
		LugarDTO ldto=lugarLogicService.getLugar(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getName(), ldto.getName());
		Assert.assertEquals(pdto.getDireccion(), ldto.getDireccion());
		Assert.assertEquals(pdto.getCosto(), ldto.getCosto());
		Assert.assertEquals(pdto.getDetalles(), ldto.getDetalles());
        
	}
	
	@Test
	public void deleteLugarTest(){
		LugarDTO pdto=data.get(0);
		lugarLogicService.deleteLugar(pdto.getId());
        LugarDTO deleted=lugarPersistence.getLugar(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateLugarTest(){
		LugarDTO pdto=data.get(0);
		
		LugarDTO ldto=new LugarDTO();
		ldto.setId(pdto.getId());
		ldto.setName(generateRandom(String.class));
		ldto.setDireccion(generateRandom(String.class));
		ldto.setCosto(generateRandom(Long.class));
		ldto.setDetalles(generateRandom(String.class));
		
		
		lugarLogicService.updateLugar(ldto);
		
		
		LugarDTO resp=lugarPersistence.getLugar(pdto.getId());
		
		Assert.assertEquals(ldto.getName(), resp.getName());	
		Assert.assertEquals(ldto.getDireccion(), resp.getDireccion());	
		Assert.assertEquals(ldto.getCosto(), resp.getCosto());	
		Assert.assertEquals(ldto.getDetalles(), resp.getDetalles());	
	}
	
	@Test
	public void getLugarPaginationTest(){
		
		LugarPageDTO dto1=lugarLogicService.getLugars(1,2);
		Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(),2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(),3L);
		
		
		LugarPageDTO dto2=lugarLogicService.getLugars(2,2);
		Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(),1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(),3L);
		
		for(LugarDTO dto:dto1.getRecords()){
        	boolean found=false;
            for(LugarDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        for(LugarDTO dto:dto2.getRecords()){
        	boolean found=false;
            for(LugarDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        LugarPageDTO dto3=lugarLogicService.getLugars(1,3);
		Assert.assertNotNull(dto3);
        Assert.assertEquals(dto3.getRecords().size(),data.size());
        Assert.assertEquals(dto3.getTotalRecords().longValue(),data.size());
		
		for(LugarDTO dto:dto3.getRecords()){
        	boolean found=false;
            for(LugarDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	
}