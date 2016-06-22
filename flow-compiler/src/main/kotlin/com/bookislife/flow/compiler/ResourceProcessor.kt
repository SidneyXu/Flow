package com.bookislife.flow.compiler

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.ws.rs.Path

/**
 * Created by SidneyXu on 2016/05/24.
 */
@SupportedAnnotationTypes("javax.ws.rs.PATH", "javax.ws.rs.POST", "javax.ws.rs.GET", "javax.ws.rs.DELETE", "javax.ws.rs.PUT")
class ResourceProcessor : AbstractProcessor() {

    override fun process(annotations: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {
        annotations.toList().forEach {
            println(it.qualifiedName)

        }
        val providedMethods = env.getElementsAnnotatedWith(Path::class.java)
        providedMethods.filter {
            it.enclosingElement.kind== ElementKind.CLASS
        }.forEach {
            println(it.simpleName)


        }
        return false
    }

}