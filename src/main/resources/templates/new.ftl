<#import "_layout.ftl" as layout />
<@layout.header>
    <div>
        <h3>Create module</h3>
        <form action="/modules" method="post">
            <p>
                <input type="text" name="idModule">
            </p>
            <p>
                <input type="text" name="title">
            </p>
            <p>
                <textarea name="description"></textarea>
            </p>
            <p>
                <input type="submit">
            </p>
        </form>
    </div>
</@layout.header>