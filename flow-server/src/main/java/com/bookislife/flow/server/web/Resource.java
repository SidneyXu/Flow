package com.bookislife.flow.server.web;

import java.util.List;

/**
 * Created by SidneyXu on 2016/05/18.
 */
public class Resource {

    public final Class<?> clazz;
    private ResourceDescriptor classDescriptor;
    private List<ResourceDescriptor> methodDescriptorList;

    public Resource(Class<?> clazz, ResourceDescriptor classDescriptor, List<ResourceDescriptor> methodDescriptorList) {
        this.clazz = clazz;
        this.classDescriptor = classDescriptor;
        this.methodDescriptorList = methodDescriptorList;
    }

    public List<ResourceDescriptor> getMethodDescriptorList() {
        return methodDescriptorList;
    }

    public ResourceDescriptor getClassDescriptor() {
        return classDescriptor;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Resource{");
        sb.append("clazz=").append(clazz);
        sb.append(", classDescriptor=").append(classDescriptor);
        sb.append(", methodDescriptorList=").append(methodDescriptorList);
        sb.append('}');
        return sb.toString();
    }
}
