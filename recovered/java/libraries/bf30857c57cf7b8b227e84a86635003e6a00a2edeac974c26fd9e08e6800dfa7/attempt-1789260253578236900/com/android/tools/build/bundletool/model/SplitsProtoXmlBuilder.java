/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.aapt.Resources;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttributeBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElementBuilder;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.google.common.collect.TreeBasedTable;

public final class SplitsProtoXmlBuilder {
    private final TreeBasedTable<String, String, String> splitsByModuleAndLanguage = TreeBasedTable.create();

    public SplitsProtoXmlBuilder addLanguageMapping(BundleModuleName module, String language, String splitId) {
        if (this.splitsByModuleAndLanguage.contains(module.getNameForSplitId(), language)) {
            throw new CommandExecutionException("Multiple module->language mapping assign.");
        }
        this.splitsByModuleAndLanguage.put((Object)module.getNameForSplitId(), (Object)language, (Object)splitId);
        return this;
    }

    public Resources.XmlNode build() {
        XmlProtoElementBuilder splitsXml = XmlProtoElementBuilder.create("splits");
        for (String moduleName : this.splitsByModuleAndLanguage.rowKeySet()) {
            XmlProtoElementBuilder languageXml = XmlProtoElementBuilder.create("language");
            this.splitsByModuleAndLanguage.row((Object)moduleName).forEach((language, splitId) -> languageXml.addChildElement(SplitsProtoXmlBuilder.createEntry(language, splitId)));
            splitsXml.addChildElement(XmlProtoElementBuilder.create("module").addAttribute(XmlProtoAttributeBuilder.create("name").setValueAsString(moduleName)).addChildElement(languageXml));
        }
        return XmlProtoNode.createElementNode(splitsXml.build()).getProto();
    }

    private static XmlProtoElementBuilder createEntry(String key, String splitId) {
        return XmlProtoElementBuilder.create("entry").addAttribute(XmlProtoAttributeBuilder.create("key").setValueAsString(key)).addAttribute(XmlProtoAttributeBuilder.create("split").setValueAsString(splitId));
    }
}

