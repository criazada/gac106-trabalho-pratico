tree = $(if $(wildcard $(1)/*),$(foreach maybedir,$(wildcard $(1)/*),$(call tree,$(maybedir))),$(1))
TREE := $(call tree,.)
JAVA_FILES := $(filter %.java,$(TREE))
MAINS = simulacao/Principal.class

run: build
	java simulacao.Principal
build: $(MAINS)
$(MAINS): $(JAVA_FILES) clean
	javac $(@:%.class=%.java)
clean:
	rm -f simulacao/*.class
