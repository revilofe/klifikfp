<#-- @ftlvariable name="module" type="com.klifikt.models.Module" -->
<#import "_layout.ftl" as layout />
<@layout.header>
    <div>
        <h3>
            ${module.idModule} - ${module.title}
        </h3>
        <p>
            ${module.description}
        </p>
        <hr>
        <p>
            <a href="/modules/${module.id}/edit">Edit module</a>
        </p>
    </div>
</@layout.header>