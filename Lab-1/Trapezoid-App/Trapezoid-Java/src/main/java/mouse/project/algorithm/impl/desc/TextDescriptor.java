package mouse.project.algorithm.impl.desc;

import mouse.project.algorithm.impl.tree.Tree;

public class TextDescriptor implements Descriptor {
    public void describe(Tree tree) {
        TextDescriptorHelper textDescriptorHelper = new TextDescriptorHelper();
        String describe = textDescriptorHelper.describe(tree);
        System.out.println(describe);
    }
}
