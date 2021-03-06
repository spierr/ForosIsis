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

package co.edu.uniandes.csw.G3xtreme.expositor.persistence;

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


import co.edu.uniandes.csw.G3xtreme.expositor.logic.dto.ExpositorPageDTO;
import co.edu.uniandes.csw.G3xtreme.expositor.logic.dto.ExpositorDTO;
import co.edu.uniandes.csw.G3xtreme.expositor.persistence.api.IExpositorPersistence;
import co.edu.uniandes.csw.G3xtreme.expositor.persistence.entity.ExpositorEntity;
import co.edu.uniandes.csw.G3xtreme.expositor.persistence.converter.ExpositorConverter;
import static co.edu.uniandes.csw.G3xtreme.util._TestUtil.*;

@RunWith(Arquillian.class)
public class ExpositorPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(ExpositorPersistence.class.getPackage())
				.addPackage(ExpositorEntity.class.getPackage())
				.addPackage(IExpositorPersistence.class.getPackage())
                .addPackage(ExpositorDTO.class.getPackage())
                .addPackage(ExpositorConverter.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IExpositorPersistence expositorPersistence;

	@PersistenceContext
	private EntityManager em;

	@Inject
	UserTransaction utx;

	@Before
	public void configTest() {
		System.out.println("em: " + em);
		try {
			utx.begin();
			clearData();
			insertData();
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				utx.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void clearData() {
		em.createQuery("delete from ExpositorEntity").executeUpdate();
	}

	private List<ExpositorEntity> data=new ArrayList<ExpositorEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			ExpositorEntity entity=new ExpositorEntity();
			entity.setName(generateRandom(String.class));
			entity.setCosto(generateRandom(Long.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createExpositorTest(){
		ExpositorDTO dto=new ExpositorDTO();
		dto.setName(generateRandom(String.class));
		dto.setCosto(generateRandom(Long.class));
		
		ExpositorDTO result=expositorPersistence.createExpositor(dto);
		
		Assert.assertNotNull(result);
		
		ExpositorEntity entity=em.find(ExpositorEntity.class, result.getId());
		
		Assert.assertEquals(dto.getName(), entity.getName());
		Assert.assertEquals(dto.getCosto(), entity.getCosto());
	}
	
	@Test
	public void getExpositorsTest(){
		List<ExpositorDTO> list=expositorPersistence.getExpositors();
		Assert.assertEquals(list.size(), data.size());
        for(ExpositorDTO dto:list){
        	boolean found=false;
            for(ExpositorEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getExpositorTest(){
		ExpositorEntity entity=data.get(0);
		ExpositorDTO dto=expositorPersistence.getExpositor(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getName(), dto.getName());
		Assert.assertEquals(entity.getCosto(), dto.getCosto());
        
	}
	
	@Test
	public void deleteExpositorTest(){
		ExpositorEntity entity=data.get(0);
		expositorPersistence.deleteExpositor(entity.getId());
        ExpositorEntity deleted=em.find(ExpositorEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateExpositorTest(){
		ExpositorEntity entity=data.get(0);
		
		ExpositorDTO dto=new ExpositorDTO();
		dto.setId(entity.getId());
		dto.setName(generateRandom(String.class));
		dto.setCosto(generateRandom(Long.class));
		
		
		expositorPersistence.updateExpositor(dto);
		
		
		ExpositorEntity resp=em.find(ExpositorEntity.class, entity.getId());
		
		Assert.assertEquals(dto.getName(), resp.getName());	
		Assert.assertEquals(dto.getCosto(), resp.getCosto());	
	}
	
	@Test
	public void getExpositorPaginationTest(){
		//Page 1
		ExpositorPageDTO dto1=expositorPersistence.getExpositors(1,2);
        Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(),2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(),3L);
        //Page 2
        ExpositorPageDTO dto2=expositorPersistence.getExpositors(2,2);
        Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(),1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(),3L);
        
        for(ExpositorDTO dto:dto1.getRecords()){
        	boolean found=false;	
            for(ExpositorEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        for(ExpositorDTO dto:dto2.getRecords()){
        	boolean found=false;
            for(ExpositorEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
}