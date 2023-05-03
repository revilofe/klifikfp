<#-- @ftlvariable name="modules" type="kotlin.collections.List<com.klifikt.models.Module>" -->
<#import "_layout.ftl" as layout />
<@layout.header>
    <#list modules?reverse as module>
        <div>
            <h3>
                <a href="/modules/${module.id}">${module.title}</a>
            </h3>
            <p>
                ${module.description}
            </p>
        </div>
    </#list>
    <hr>
    <p>
        <a href="/modules/new">Create module</a>
    </p>
</@layout.header>