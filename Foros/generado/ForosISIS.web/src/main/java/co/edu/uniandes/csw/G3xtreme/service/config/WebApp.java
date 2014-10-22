package co.edu.uniandes.csw.G3xtreme.service.config;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/webresources")
public class WebApp extends Application {

    private Set<Class<?>> classes;

    @Override
    public Set<Class<?>> getClasses() {
        if (classes == null) {
            try {
                classes = new LinkedHashSet<Class<?>>();
                ImmutableSet<ClassPath.ClassInfo> topLevelClasses = ClassPath.from(WebApp.class.getClassLoader()).getTopLevelClassesRecursive("co.edu.uniandes.csw.G3xtreme");
                for (ClassPath.ClassInfo classInfo : topLevelClasses) {
                	if(classInfo.getSimpleName().endsWith("Service") && classInfo.getPackageName().contains("service")){
                       classes.add(classInfo.load());
					}                
                }
                
                return classes;
            } catch (IOException ex) {
                Logger.getLogger(WebApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return classes;
    }

}