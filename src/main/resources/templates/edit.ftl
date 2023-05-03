<#-- @ftlvariable name="module" type="com.klifikt.models.Article" -->
<#import "_layout.ftl" as layout />
<@layout.header>
    <div>
        <h3>Edit module</h3>
        <form action="/modules/${module.id}" method="post">
            <p>
                <input type="text" name="idModule" value="${module.idModule}">
            </p>
            <p>
                <input type="text" name="title" value="${module.title}">
            </p>
            <p>
                <textarea name="description">${module.description}</textarea>
            </p>
            <p>
                <input type="submit" name="_action" value="update">
            </p>
        </form>
    </div>
    <div>
        <form action="/modules/${module.id}" method="post">
            <p>
                <input type="submit" name="_action" value="delete">
            </p>
        </form>
    </div>
</@layout.header>