package com.galanz.processors;

import com.galanz.processors.anno.BindView;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

public class GalanzProcessor extends AbstractProcessor {
    private Map<String, List<VariableElement>> mCacheMap;
    /**
     * 文件相关的辅助类
     */
    private Filer mFiler;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mCacheMap = new HashMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        parserBindView(set,roundEnvironment);
        return true;
    }



    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<String>();
        annotataions.add(BindView.class.getCanonicalName());
        return annotataions;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private String getActivityName(VariableElement variableElement) {
        String packageName = getPackageName(variableElement);
        TypeElement typeElement= (TypeElement) variableElement.getEnclosingElement();
        String className = typeElement.getSimpleName().toString();
        return packageName+"."+className ;
    }
    private String getPackageName(VariableElement variableElement) {
        TypeElement typeElement= (TypeElement) variableElement.getEnclosingElement();
        String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
        return packageName;
    }
    private void writerHeader(Writer writer, String packageName, String activityName, String newActivitySimpleName) {
        try {
            writer.write("package "+packageName+";");
            writer.write("\n");
            writer.write("import com.galanz.processors.binder.ViewBinder;");
            writer.write("\n");
            writer.write("public class " + newActivitySimpleName +
                    " implements  ViewBinder<" + activityName + "> {");
            writer.write("\n");
            writer.write(" public void bind( "+activityName+" target) {");
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parserBindView(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementSet=roundEnvironment.getElementsAnnotatedWith(BindView.class);
        if (elementSet.size()==0){
            return ;
        }
        for (Element element : elementSet) {
            VariableElement variableElement = (VariableElement) element;
            String  activityName= getActivityName(variableElement);
            List<VariableElement> list = mCacheMap.get(activityName);
            if (list == null) {
                list=new ArrayList<>();
                mCacheMap.put(activityName, list);
            }
            list.add(variableElement);
            System.out.println(""+variableElement.getSimpleName().toString());
        }
        Iterator iterator=mCacheMap.keySet().iterator();
        while (iterator.hasNext()) {
            String activityName = (String) iterator.next();
            System.out.println("activityName:"+activityName);
            List<VariableElement> caheElements = mCacheMap.get(activityName);
            String newActivityBinder = activityName+"$ViewBinder";
            System.out.println("newActivityBinder:"+newActivityBinder);
            String packageName = getPackageName(caheElements.get(0));
            System.out.println("packageName:"+packageName);
            String newActivitySimpleName = caheElements.get(0).getEnclosingElement().getSimpleName().toString()+"$ViewBinder";
            System.out.println("newActivitySimpleName:"+newActivitySimpleName);
            try {
                JavaFileObject javaFileObject=mFiler.createSourceFile(newActivityBinder);
                Writer writer= javaFileObject.openWriter();
                writerHeader(writer,packageName,activityName,newActivitySimpleName);
                for (VariableElement variableElement : caheElements) {
                    BindView bindView = variableElement.getAnnotation(BindView.class);
                    int id = bindView.value();
                    String fieldName = variableElement.getSimpleName().toString();
                    TypeMirror typeMirror=variableElement.asType();
                    writer.write("target." + fieldName + "=(" + typeMirror.toString() + ")target.findViewById(" + id + ");");
                    writer.write("\n");
                }
                //结尾部分
                writer.write("\n");
                writer.write("}");
                writer.write("\n");
                writer.write("}");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
