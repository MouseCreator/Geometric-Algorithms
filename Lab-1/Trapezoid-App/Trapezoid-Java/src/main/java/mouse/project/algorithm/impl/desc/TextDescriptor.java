package mouse.project.algorithm.impl.desc;

import mouse.project.algorithm.common.CommonGraph;
import mouse.project.algorithm.impl.tree.Tree;

public class TextDescriptor implements Descriptor<String> {
    public String describe(Tree tree) {
        TextDescriptorHelper textDescriptorHelper = new TextDescriptorHelper();
        String describe = textDescriptorHelper.describe(tree);
        System.out.println(describe);
        return describe;
    }

    @Override
    public void inspect(CommonGraph commonGraph) {

    }
}
