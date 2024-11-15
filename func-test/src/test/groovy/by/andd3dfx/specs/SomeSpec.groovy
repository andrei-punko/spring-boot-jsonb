package by.andd3dfx.specs

import groovyx.net.http.HttpResponseException
import org.apache.commons.lang.RandomStringUtils
import spock.lang.Specification

import static by.andd3dfx.configs.Configuration.restClient

class SomeSpec extends Specification {

    def 'Read all articles'() {
        when: 'get all articles'
        def getResponse = restClient.get(path: '/articles/all')

        then: 'server returns 200 code (ok)'
        assert getResponse.status == 200
        and: 'got 10 records'
        assert getResponse.responseData.content.size() == 10
    }

    def 'Read all articles using pagination'() {
        when: 'get articles for page=4, pageSize=2 sorted by title,DESC'
        def getResponse = restClient.get(
                path: '/articles/all',
                query: [
                        size: '2',
                        page: '4',
                        sort: 'title,DESC'
                ]
        )

        then: 'server returns 200 code (ok)'
        assert getResponse.status == 200
        and: 'got 2 records'
        assert getResponse.responseData.content.size() == 2
    }

    def 'Search articles by an inner field of JSONB type'() {
        when: 'get articles for country=RU, city=Moscow'
        def getResponse = restClient.get(
                path: '/articles',
                query: [
                        country: 'RU',
                        city: 'Moscow'
                ]
        )

        then: 'server returns 200 code (ok)'
        assert getResponse.status == 200
        and: 'got 1 record'
        assert getResponse.responseData.content.size() == 1
        and: 'title is "Игрок"'
        assert getResponse.responseData[0].title == 'Игрок'
        and: 'location.country is "RU"'
        assert getResponse.responseData[0].location.country == 'RU'
        and: 'location.city is "Moscow"'
        assert getResponse.responseData[0].location.city == 'Moscow'
    }

    def 'Read particular article'() {
        when: 'get particular article by id'
        def getResponse = restClient.get(path: '/articles/' + id)

        then: 'server returns 200 code (ok)'
        assert getResponse.status == 200
        and: 'got expected article'
        assert getResponse.responseData.title == title
        assert getResponse.responseData.summary == summary
        assert getResponse.responseData.author == author

        where:
        id | title                              | summary                          | author
        1  | 'Игрок'                            | 'Рассказ о страсти игромании'    | 'Федор Достоевский'
        8  | 'Сила Божия и немощь человеческая' | 'Жизнеописание игумена Феодосия' | 'Сергей Нилус'
        9  | 'Отечник'                          | 'Цитаты Святых Отцов'            | 'Игнатий Брянчанинов'
        10 | 'Душеполезные поучения'            | 'Азбука духовной жизни'          | 'Авва Дорофей'
    }

    def 'Create an article'() {
        when: 'create an article'
        def createResponse = restClient.post(
                path: '/articles',
                body: [
                        title  : 'Some new title',
                        summary: 'Bla-bla summary',
                        text   : 'BomBiBom',
                        author : 'Сергей Нилус'
                ],
                requestContentType: 'application/json'
        )

        then: 'got 201 status'
        assert createResponse.status == 201
        and: 'got created article details in response'
        assert createResponse.responseData.title == 'Some new title'
        assert createResponse.responseData.summary == 'Bla-bla summary'
        assert createResponse.responseData.text == 'BomBiBom'

        cleanup: 'delete created article'
        restClient.delete(path: '/articles/' + createResponse.responseData.id)
    }

    def 'Delete an article'() {
        setup: 'create an article'
        def createResponse = restClient.post(
                path: '/articles',
                body: [
                        title  : generateRandomString(10),
                        summary: generateRandomString(10),
                        text   : 'Weird text',
                        author : 'Сергей Нилус'
                ],
                requestContentType: 'application/json'
        )
        and: 'got 201 status'
        assert createResponse.status == 201
        def id = createResponse.responseData.id

        when: 'delete just created article'
        def deleteResponse = restClient.delete(path: '/articles/' + id)

        then: 'server returns 204 code'
        assert deleteResponse.status == 204

        and: 'couldn\'t get deleted article by id, got 404 error instead'
        try {
            restClient.get(path: '/articles/' + id)
            throw new RuntimeException("Should not found deleted article")
        } catch (HttpResponseException hre) {
            assert hre.statusCode == 404
        }
    }

    def 'Update an article'() {
        when: 'update article.title for article with id=2'
        def newTitle = generateRandomString(10)
        def updateResponse = restClient.patch(
                path: '/articles/2',
                body: [title: newTitle],
                requestContentType: 'application/json'
        )

        then: 'got 200 status'
        assert updateResponse.status == 200

        and: 'read an article'
        def getResponse = restClient.get(path: '/articles/2')
        and: 'got 200 status'
        assert getResponse.status == 200
        and: 'got article title equals to new value'
        assert getResponse.responseData.title == newTitle
    }

    private static String generateRandomString(int count) {
        return RandomStringUtils.random(count, true, true)
    }
}
