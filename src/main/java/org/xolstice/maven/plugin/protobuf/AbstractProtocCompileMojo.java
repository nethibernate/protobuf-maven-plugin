package org.xolstice.maven.plugin.protobuf;

/*
 * Copyright (c) 2019 Maven Protocol Buffers Plugin Authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * An abstract base mojo configuration for using {@code protoc} compiler with the main sources.
 *
 * @since 0.3.3
 */
public abstract class AbstractProtocCompileMojo extends AbstractProtocMojo {

    /**
     * The source directories containing the {@code .proto} definitions to be compiled.
     */
    @Parameter(
            required = true,
            defaultValue = "${basedir}/src/main/proto"
    )
    private File protoSourceRoot;

    @Parameter(required = true)
    private File[] protoSources = {};

    @Override
    protected void doAttachProtoSources() {
        projectHelper.addResource(project, getProtoSourceRoot().getAbsolutePath(),
                asList(getIncludes()), asList(getExcludes()));
    }

    @Override
    protected void doAttachGeneratedFiles() {
        final File outputDirectory = getOutputDirectory();
        project.addCompileSourceRoot(outputDirectory.getAbsolutePath());
        buildContext.refresh(outputDirectory);
    }

    @Override
    protected List<Artifact> getDependencyArtifacts() {
        return project.getCompileArtifacts();
    }

    @Override
    protected File getProtoSourceRoot() {
        return protoSourceRoot;
    }

    @Override
    protected File[] getProtoSources() {
        return this.protoSources;
    }
}
