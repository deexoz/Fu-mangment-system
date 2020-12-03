import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ProjecunitmangmentTestModule } from '../../../test.module';
import { FileDetailComponent } from 'app/entities/file/file-detail.component';
import { File } from 'app/shared/model/file.model';

describe('Component Tests', () => {
  describe('File Management Detail Component', () => {
    let comp: FileDetailComponent;
    let fixture: ComponentFixture<FileDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ file: new File(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProjecunitmangmentTestModule],
        declarations: [FileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load file on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.file).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
